package ru.gozhan.lab05.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import ru.gozhan.lab05.R;
import ru.gozhan.lab05.constant.CourierAbility;
import ru.gozhan.lab05.model.Courier;
import ru.gozhan.lab05.model.Order;
import ru.gozhan.lab05.model.pack.DocumentPackage;
import ru.gozhan.lab05.model.pack.HugePackage;
import ru.gozhan.lab05.model.pack.Package;
import ru.gozhan.lab05.model.pack.SmallPackage;

public class OrderAdapter extends BaseAdapter {

    private Courier courier;
    private ArrayList<Order> orders;
    private LayoutInflater layoutInflater;
    private boolean isCourierAvailable = false;

    public OrderAdapter(Context context, ArrayList<Order> orders, Courier courier) {
        this.orders = orders;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.courier = courier;
    }

    @Override
    public int getCount() {
        return this.orders.size();
    }

    @Override
    public Object getItem(int position) {
        return this.orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.order_item, parent, false);
        }

        TextView name_item = view.findViewById(R.id.name_item);
        TextView pack_item = view.findViewById(R.id.pack_item);
        TextView from_item = view.findViewById(R.id.from_item);
        TextView to_item = view.findViewById(R.id.to_item);
        TextView cost_item = view.findViewById(R.id.cost_item);
        CheckBox checkBox = view.findViewById(R.id.checkBox);

        Order order = getOrder(position);

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isCourierAvailable = false;

            Package pack = ((Order) getItem(position)).getPack();
            if (pack instanceof HugePackage && courier.hasAbility(CourierAbility.CAR_DELIVERY)) {
                isCourierAvailable = true;
            } else if (pack instanceof DocumentPackage && courier.hasAbility(CourierAbility.DOCS_DELIVERY)) {
                isCourierAvailable = true;
            } else if (pack.isFragile() && courier.hasAbility(CourierAbility.FRAGILE_DELIVERY)) {
                isCourierAvailable = true;
            } else if (pack instanceof SmallPackage && !pack.isFragile()) {
                isCourierAvailable = true;
            }

            if (isCourierAvailable && isChecked) {
                order.setSelected(true);
            } else {
                checkBox.setChecked(false);
                order.setSelected(false);
            }
            notifyDataSetChanged();
        });

        name_item.setText(order.getCompany().getName());
        pack_item.setText(order.getPack().getType());
        from_item.setText(order.getDepartureAddress());
        to_item.setText(order.getDeliveryAddress());
        cost_item.setText(String.valueOf(order.getPrice()));
        checkBox.setChecked(order.isSelected());

        boolean flag = false;
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).isSelected()) {
                flag = true;
            }
        }
        if (!flag) {
            checkBox.setChecked(false);
        }

        return view;
    }

    private Order getOrder(int position) {
        return (Order) getItem(position);
    }

    public void updateRecords(ArrayList<Order> orders, Courier courier) {
        this.orders = orders;
        this.courier = courier;
        notifyDataSetChanged();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

}
