package ru.gozhan.lab05;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ru.gozhan.lab05.adapter.OrderAdapter;
import ru.gozhan.lab05.model.Company;
import ru.gozhan.lab05.model.Courier;
import ru.gozhan.lab05.model.Order;
import ru.gozhan.lab05.model.pack.Package;
import ru.gozhan.lab05.util.CreateAllObjects;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Courier courier = new Courier("Гожан Александр", "88005353535", "");

        TextView textView = findViewById(R.id.courier_name);
        textView.setText(courier.getName());

        TextView textView1 = findViewById(R.id.courier_paymentAccount);
        textView1.setText(courier.getPaymentAccount());

        TextView textView2 = findViewById(R.id.courier_abilities);
        textView2.setText(courier.getAbilities());

        ArrayList<Company> companies = (ArrayList<Company>) CreateAllObjects.createCompanies();
        ArrayList<Package> packages = (ArrayList<Package>) CreateAllObjects.createPackages();
        ArrayList<Order> orders = (ArrayList<Order>) CreateAllObjects.createOrders(companies, packages);

        courier.setOrders(orders);

        listView = findViewById(R.id.listView);

        OrderAdapter adapter = new OrderAdapter(this, courier.getOrders());

        listView.setAdapter(adapter);
        Button btn_ok = findViewById(R.id.button_ok);
        Button btn_clear = findViewById(R.id.button_clear);

        btn_ok.setOnClickListener(v -> {
            double result = 0;
            for (int i = 0; i < adapter.getOrders().size(); i++) {
                if (adapter.getOrders().get(i).isSelected()) {
                    result += (adapter.getOrders().get(i).getPrice());
                }
            }
            showInfo(result);
        });

        btn_clear.setOnClickListener(v -> {

            for (int i = 0; i < adapter.getOrders().size(); i++) {
                adapter.getOrders().get(i).setSelected(false);
            }
            adapter.notifyDataSetChanged();

        });
    }

    private void showInfo(double cost) {
        Toast.makeText(this, "Total: " + cost, Toast.LENGTH_LONG).show();
    }

}
