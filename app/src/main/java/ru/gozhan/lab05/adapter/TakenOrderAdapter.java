package ru.gozhan.lab05.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import ru.gozhan.lab05.R;
import ru.gozhan.lab05.model.Order;

public class TakenOrderAdapter extends ArrayAdapter<Order> {

    private Context context;
    private ArrayList<Order> orders;

    public TakenOrderAdapter(Context context, ArrayList<Order> orders) {
        super(context, R.layout.taken_order_item, orders);
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(context).inflate(R.layout.taken_order_item, parent, false);

        Order currentOrder = orders.get(position);

        TextView name = listItem.findViewById(R.id.name_item);
        name.setText(currentOrder.getCompany().getName());

        TextView pack = listItem.findViewById(R.id.pack_item);
        pack.setText(currentOrder.getPack().getType());

        TextView from = listItem.findViewById(R.id.from_item);
        from.setText(currentOrder.getDepartureAddress());

        TextView to = listItem.findViewById(R.id.to_item);
        to.setText(currentOrder.getDeliveryAddress());

        TextView cost = listItem.findViewById(R.id.cost_item);
        cost.setText(String.valueOf(currentOrder.getPrice()));

//        name_item.setText(order.getCompany().getName());
//        pack_item.setText(order.getPack().getType());
//        from_item.setText(order.getDepartureAddress());
//        to_item.setText(order.getDeliveryAddress());
//        cost_item.setText(String.valueOf(order.getPrice()));
//        checkBox.setChecked(order.isSelected());

        return listItem;
    }
}
