package com.example.adm.Fragments.Orders.BottomSheet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adm.Classes.MyLog;
import com.example.adm.Fragments.Orders.BottomSheet.Classes.OrderDishLists;
import com.example.adm.Fragments.Orders.BottomSheet.Classes.OrderHeaderLists;
import com.example.adm.Fragments.Orders.Classes.SelectedDateList;
import com.example.adm.Fragments.Orders.Classes.SelectedSessionList;
import com.example.adm.R;
import com.example.adm.ViewModel.GetViewModel;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class ViewCartAdapterDate extends RecyclerView.Adapter<ViewCartAdapterDate.ViewHolder> {
    private Context context;
    private String TAG = "ViewCartAdapterDate";
    private GetViewModel getViewModel;

    //order hashmap
    //Date map
    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<OrderDishLists>>>>> orderDateMap = new LinkedHashMap<>();
    //Session map
    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<OrderDishLists>>>> orderSessionMap = new LinkedHashMap<>();
    //Header map
    private LinkedHashMap<String, LinkedHashMap<String, List<OrderDishLists>>> orderHeaderMap = new LinkedHashMap<>();
    //Item map
    private LinkedHashMap<String, List<OrderDishLists>> orderItemMap = new LinkedHashMap<>();
    //selected headers
    private List<OrderHeaderLists> o_Order_HeaderLists =new ArrayList<>();
    private List<SelectedSessionList> o_selectedSessionLists=new ArrayList<>();
    private List<SelectedDateList> o_dateLists=new ArrayList<>();
    private String name,func;

    public ViewCartAdapterDate(Context context, GetViewModel getViewModel, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<OrderDishLists>>>>> orderDateMap, List<SelectedDateList> o_dateLists, String name, String func) {
        this.context = context;
        this.getViewModel = getViewModel;
        this.func = func;
        this.name = name;
        this.orderDateMap = new LinkedHashMap<>(orderDateMap);
        this.o_dateLists = new ArrayList<>(o_dateLists);
    }


    @NonNull
    @Override
    public ViewCartAdapterDate.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.bottom_sheet_order_date, parent, false);
        return new ViewCartAdapterDate.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCartAdapterDate.ViewHolder holder, int position) {

        final SelectedDateList o_dateLists1=o_dateLists.get(position);
        holder.date.setText(o_dateLists1.getDate());
        MyLog.e(TAG,"orders>> o_dateLists1.getDate()>>"+o_dateLists1.getDate());

        //get order session map
        orderSessionMap=new LinkedHashMap<>(orderDateMap).get(o_dateLists1.getDate());

        Set<String> set = orderSessionMap.keySet();
        List<String> aList1 = new ArrayList<String>(set.size());
        for (String x1 : set)
            aList1.add(x1);
        o_selectedSessionLists=new ArrayList<>();
        for(int i=0;i<aList1.size();i++) {
            SelectedSessionList sessionList = new SelectedSessionList();

            String[] be = (aList1.get(i)).split("_");
            String bolen=be[1];
            String[] scb = (be[0]).split("-");
            String count=scb[1];
            String[] se=(scb[0]).split("!");
            String sess=se[0];
            String time=se[1];
            sessionList.setSession_title(sess);
            sessionList.setTime(time);
            sessionList.setBolen(bolen);
            sessionList.setCount(count);
            o_selectedSessionLists.add(sessionList);

        }


        holder.recyclerview_order_session_deatils.setHasFixedSize(true);
        holder.recyclerview_order_session_deatils.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        ViewCartAdapterSession viewCartAdapter = new ViewCartAdapterSession(context, getViewModel,orderSessionMap,o_dateLists1.getDate(),o_selectedSessionLists,name,func);
        holder.recyclerview_order_session_deatils.setAdapter(viewCartAdapter);

    }

    @Override
    public int getItemCount() {

        return o_dateLists.size();

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private RecyclerView recyclerview_order_session_deatils;


        public ViewHolder(View view) {
            super(view);
            recyclerview_order_session_deatils = view.findViewById(R.id.recyclerview_order_session_deatils);
            date = view.findViewById(R.id.date);


        }
    }
}

