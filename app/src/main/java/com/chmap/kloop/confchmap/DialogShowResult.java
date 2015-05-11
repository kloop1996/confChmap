package com.chmap.kloop.confchmap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kloop on 12.04.2015.
 */
public class DialogShowResult extends DialogFragment {
    // названия компаний (групп)
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
    static HashMap<String,int[]> recomend;

    static ArrayList<String> getRecomend (String key){
        ArrayList<String> data=new ArrayList<String>();

        for (int i:recomend.get(key))
        {
            data.add(permActions[i]);
        }

        return data;
    }

    // общая коллекция для коллекций элементов
    ArrayList<ArrayList<Map<String, String>>> childData;
    // в итоге получится childData = ArrayList<childDataItem>

    // список аттрибутов группы или элемента
    Map<String, String> m;

    ExpandableListView elvMain;

    public static
    <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_show_result, null);
        builder.setView(view);


        recomend=new HashMap<String,int[]>();
        recomend.put("less 0.1",new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12});
        recomend.put("0.1-0.2 ku/km2",new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12});
        recomend.put("0.2-0.5 ku/km",new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12});
        recomend.put("0.5-1 ku/km2",new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12});
        recomend.put("1-5 ku/km2",new int[]{3,5,6,9,11,12});
        recomend.put("5-15 ku/km",new int[]{3,9,12});
        recomend.put("15-40 ku/km2",new int[]{13});
        recomend.put("over 40 ku/km2",new int[]{13});
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
        Collection<String> unsorted=FragmentGpsSearch.getPolution().keySet();
        for (String phone : asSortedList(unsorted)) {
            m = new HashMap<String, String>();

            m.put("phoneName", phone+" год: "+FragmentGpsSearch.getPolution().get(phone)); // название телефона
            childDataItem.add(m);
        }
        // добавляем в коллекцию коллекций
        childData.add(childDataItem);

        // создаем коллекцию элементов для второй группы
        childDataItem = new ArrayList<Map<String, String>>();
        for (InfAboutCity phone : FragmentGpsSearch.getNearCities()) {
            m = new HashMap<String, String>();
            m.put("phoneName", phone.name+" "+String.format("%1$.2f", phone.distance)+" км");
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        // создаем коллекцию элементов для третьей группы
        childDataItem = new ArrayList<Map<String, String>>();
        for (String phone : getRecomend(FragmentGpsSearch.getPolution().get("1986"))) {
            m = new HashMap<String, String>();
            m.put("phoneName", phone);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        childDataItem = new ArrayList<Map<String, String>>();

            m = new HashMap<String, String>();
            m.put("phoneName", "Область: "+FragmentGpsSearch.getInf().get(0));
            childDataItem.add(m);
        m = new HashMap<String, String>();
            m.put("phoneName","Широта: "+FragmentGpsSearch.getInf().get(1));
            childDataItem.add(m);
        m = new HashMap<String, String>();
            m.put("phoneName","Долгота: "+FragmentGpsSearch.getInf().get(2));
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
