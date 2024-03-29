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
import com.example.adm.Fragments.Orders.BottomSheet.Classes.OrderLists;
import com.example.adm.Fragments.Orders.BottomSheet.Classes.OrderHeaderLists;
import com.example.adm.Fragments.Orders.Classes.SelectedSessionList;
import com.example.adm.R;
import com.example.adm.ViewModel.GetViewModel;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class ViewCartAdapterSession extends RecyclerView.Adapter<ViewCartAdapterSession.ViewHolder> {
    private Context context;
    private String TAG = "ViewCartAdapterSession";
    private List<OrderHeaderLists> o_Order_HeaderLists = new ArrayList<>();
    private List<SelectedSessionList> o_selectedSessionLists = new ArrayList<>();
    private GetViewModel getViewModel;
    private OrderLists orderLists;

    //order hash map
    //Session map
    private LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<OrderDishLists>>>> orderSessionMap = new LinkedHashMap<>();
    //Header map
    private LinkedHashMap<String, LinkedHashMap<String, List<OrderDishLists>>> orderHeaderMap = new LinkedHashMap<>();
    //Item map
    private LinkedHashMap<String, List<OrderDishLists>> orderItemMap = new LinkedHashMap<>();
    //order item list
    private List<OrderDishLists> o_orderDishLists = new ArrayList<>();
    private String name, func, date;


    public ViewCartAdapterSession(Context context, GetViewModel getViewModel, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<OrderDishLists>>>> orderSessionMap, String date, List<SelectedSessionList> o_selectedSessionLists, String name, String func) {
        this.context = context;
        this.getViewModel = getViewModel;
        this.orderSessionMap = new LinkedHashMap<>(orderSessionMap);
        this.name = name;
        this.o_selectedSessionLists = o_selectedSessionLists;
        this.func = func;
        this.date = date;

    }


    @NonNull
    @Override
    public ViewCartAdapterSession.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.bottom_sheet_order_session, parent, false);
        return new ViewCartAdapterSession.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCartAdapterSession.ViewHolder holder, int position) {
        final SelectedSessionList list = o_selectedSessionLists.get(position);
        holder.session_title.setText(list.getSession_title());
        holder.count.setText(list.getCount());
        holder.time.setText(list.getTime());

        String s = list.getSession_title() + "!" + list.getTime() + "-" + list.getCount() + "_" + list.getBolen();
        MyLog.e(TAG, "bottom>>sess>" + s);
        orderHeaderMap = new LinkedHashMap<>(orderSessionMap).get(s);

        //get header list
        Set<String> stringSet = orderHeaderMap.keySet();

        List<String> aList = new ArrayList<String>(stringSet.size());
        for (String x : stringSet)
            aList.add(x);
        o_Order_HeaderLists = new ArrayList<>();
        for (int k = 0; k < aList.size(); k++) {
            OrderHeaderLists orderHeaderLists = new OrderHeaderLists(
                    aList.get(k)
            );
            o_Order_HeaderLists.add(orderHeaderLists);
        }

        holder.recyclerview_order_item_details.setHasFixedSize(true);
        holder.recyclerview_order_item_details.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        ViewCartAdapterHeader itemListAdapters = new ViewCartAdapterHeader(context, getViewModel, o_Order_HeaderLists, orderHeaderMap,name,func,date,s);
        holder.recyclerview_order_item_details.setAdapter(itemListAdapters);


    }


    @Override
    public int getItemCount() {
        return o_selectedSessionLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView session_title, count, time;
        private RecyclerView recyclerview_order_item_details;


        public ViewHolder(View view) {
            super(view);
            recyclerview_order_item_details = view.findViewById(R.id.recyclerview_order_item_details);
            session_title = view.findViewById(R.id.session_title);
            count = view.findViewById(R.id.count);
            time = view.findViewById(R.id.time);

        }
    }
}

