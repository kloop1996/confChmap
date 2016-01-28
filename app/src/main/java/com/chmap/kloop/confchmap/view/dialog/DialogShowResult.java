package com.chmap.kloop.confchmap.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.controller.BackgroundDefinePolution;
import com.chmap.kloop.confchmap.dao.database.cache.CacheInfmapDatabase;
import com.chmap.kloop.confchmap.entity.City;
import com.chmap.kloop.confchmap.entity.Polution;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.chmap.kloop.confchmap.service.impl.CoordinateService;
import com.chmap.kloop.confchmap.service.impl.MapProviderService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kloop on 17.12.2015.
 */
public class DialogShowResult extends DialogFragment {
    String[] groups = new String[] {"Загрязнение по годам", "Ближайшие населенные пункты", "Рекомендации","Информация о точке"};

    // названия телефонов (элементов)
    String[] phonesHTC = new String[] {"Sensation", "Desire", "Wildfire", "Hero"};
    String[] phonesSams = new String[] {"Galaxy S II", "Galaxy Nexus", "Wave"};
    String[] phonesLG = new String[] {"Optimus", "Optimus Link", "Optimus Black", "Optimus One"};
    static String[] permActions = new String[] {"Сбор грибов-аккумуляторов и сильно накапливающих радиоцезий грибов", "Сбор средне- и слабонакапливающих радиоцезий грибов", "Заготовка лесных ягод и плодов", "Ведение пчеловодства","Заготовка лекарственного сырья","Заготовка технического сырья","Выпас откормочного и рабочего скота и заготовка сена для него","Выпас молочного скота и заготовка сена для него","Заготовка хвойной лапки и веточного корма","Охота и рыбная ловля","Заготовка мха","Заготовка новогодних елок","Заготовка березового сока","Запрещена любая хоз. деятельность"};
    // коллекция для групп
    ArrayList<Map<String, String>> groupData;

    // коллекция для элементов одной группы
    ArrayList<Map<String, String>> childDataItem;
    static HashMap<Double,int[]> recomend;

    static ArrayList<String> getRecomend (double key){
        ArrayList<String> data=new ArrayList<String>();

        for (int i:recomend.get(key))
        {
            data.add(permActions[i]);
        }

        return data;
    }


    ArrayList<ArrayList<Map<String, String>>> childData;

    Map<String, String> m;

    ExpandableListView elvMain;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_show_result, null);
        builder.setView(view);


        recomend=new HashMap<Double,int[]>();
        recomend.put(0.0,new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12});
        recomend.put(0.1,new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12});
        recomend.put(0.2,new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12});
        recomend.put(0.5,new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12});
        recomend.put(1.0,new int[]{3,5,6,9,11,12});
        recomend.put(5.0,new int[]{3,9,12});
        recomend.put(15.0,new int[]{13});
        recomend.put(40.0,new int[]{13});
        groupData = new ArrayList<Map<String, String>>();
        for (String group : groups) {
            // заполняем список аттрибутов для каждой группы
            m = new HashMap<String, String>();
            m.put("groupName", group); // имя компании
            groupData.add(m);
        }

        // список аттрибутов групп для чтения
        String groupFrom[] = new String[] {"groupName"};
        // список ID view-элементов, в которые будет помещены аттрибуты групп
        int groupTo[] = new int[] {android.R.id.text1};


        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<Map<String, String>>>();

        // создаем коллекцию элементов для первой группы
        childDataItem = new ArrayList<Map<String, String>>();
        // заполняем список аттрибутов для каждого элемента


        ArrayList<Polution> polutions = BackgroundDefinePolution.getResultPolution();
        for (Polution polution:polutions) {
            m = new HashMap<String, String>();

            m.put("phoneName", polution.getYear()+" год: "+polution.getLevel().getStartValue()+" - "+polution.getLevel().getEndValue()); // название телефона
            childDataItem.add(m);
        }
        // добавляем в коллекцию коллекций
        childData.add(childDataItem);

        // создаем коллекцию элементов для второй группы
        childDataItem = new ArrayList<Map<String, String>>();
        ArrayList<City> cities = BackgroundDefinePolution.getResultNearCity();

        for (City city:cities) {
            m = new HashMap<String, String>();
            m.put("phoneName", city.getName()+" "+String.format("%1$.2f", city.getDistance())+" км");
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        // создаем коллекцию элементов для третьей группы
        childDataItem = new ArrayList<Map<String, String>>();
        for (String phone : getRecomend(polutions.get(0).getLevel().getStartValue())) {
            m = new HashMap<String, String>();
            m.put("phoneName", phone);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        childDataItem = new ArrayList<Map<String, String>>();

        m = new HashMap<String, String>();
        try {
            m.put("phoneName", "Область: " + CacheInfmapDatabase.getLocaleName((MapProviderService.getIdOfLocale(BackgroundDefinePolution.getCurrentPosition()))));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        childDataItem.add(m);
        m = new HashMap<String, String>();
        m.put("phoneName", "Широта: " + BackgroundDefinePolution.getCurrentPosition().getLatitude());
        childDataItem.add(m);
        m = new HashMap<String, String>();
        m.put("phoneName","Долгота: "+BackgroundDefinePolution.getCurrentPosition().getLongitude());
        childDataItem.add(m);

        childData.add(childDataItem);

        // список аттрибутов элементов для чтения
        String childFrom[] = new String[] {"phoneName"};
        // список ID view-элементов, в которые будет помещены аттрибуты элементов
        int childTo[] = new int[] {android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                getActivity(),
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);

        elvMain = (ExpandableListView) view.findViewById(R.id.elvMain);
        elvMain.setAdapter(adapter);
        return builder.create();
    }
}
