package ru.gozhan.lab05;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.gozhan.lab05.adapter.OrderAdapter;
import ru.gozhan.lab05.constant.CourierAbility;
import ru.gozhan.lab05.model.Company;
import ru.gozhan.lab05.model.Courier;
import ru.gozhan.lab05.model.Order;
import ru.gozhan.lab05.model.pack.DocumentPackage;
import ru.gozhan.lab05.model.pack.HugePackage;
import ru.gozhan.lab05.model.pack.Package;
import ru.gozhan.lab05.model.pack.SmallPackage;
import ru.gozhan.lab05.util.CreateAllObjects;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Order> orders;
    private ArrayList<Order> displayedOrders;

    private Courier courier;
    private ArrayList<CourierAbility> selectedAbilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        selectedAbilities = new ArrayList<>();

        courier = new Courier(
                "Gozhan Alexandr",
                "88005353535",
                selectedAbilities);

        setCourierInfo(courier);

        ArrayList<Company> companies = (ArrayList<Company>) CreateAllObjects.createCompanies();
        ArrayList<Package> packages = (ArrayList<Package>) CreateAllObjects.createPackages();
        orders = (ArrayList<Order>) CreateAllObjects.createOrders(companies, packages);

        OrderAdapter adapter = new OrderAdapter(this, orders, courier);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Button btn_show_all_orders = findViewById(R.id.button_show_all);
        Button button_show_available = findViewById(R.id.button_show_available);
        Button btn_ok = findViewById(R.id.button_ok);
        Button btn_clear = findViewById(R.id.button_clear);

        btn_show_all_orders.setOnClickListener(v -> displayAllOrders(adapter));

        button_show_available.setOnClickListener(v -> displayAvailableOrders(adapter));

        btn_ok.setOnClickListener(v -> {
            double totalCash = 0;
            for (int i = 0; i < adapter.getOrders().size(); i++) {
                if (adapter.getOrders().get(i).isSelected()) {
                    totalCash += (adapter.getOrders().get(i).getPrice());
                }
            }
            showInfo(totalCash);
        });

        btn_clear.setOnClickListener(v -> {
            for (int i = 0; i < adapter.getOrders().size(); i++) {
                adapter.getOrders().get(i).setSelected(false);
            }
            adapter.notifyDataSetChanged();
        });

        displayAvailableOrders(adapter);
    }

    private void displayAvailableOrders(OrderAdapter adapter) {
        displayedOrders = new ArrayList<>();
        for (Order order : orders) {
            Package pack = order.getPack();
            if (pack instanceof HugePackage && courier.hasAbility(CourierAbility.CAR_DELIVERY)) {
                displayedOrders.add(order);
            } else if (pack instanceof DocumentPackage && courier.hasAbility(CourierAbility.DOCS_DELIVERY)) {
                displayedOrders.add(order);
            } else if (pack.isFragile() && courier.hasAbility(CourierAbility.FRAGILE_DELIVERY)) {
                displayedOrders.add(order);
            } else if (pack instanceof SmallPackage && !pack.isFragile()) {
                displayedOrders.add(order);
            }
        }
        adapter.updateRecords(displayedOrders, courier);

        if (displayedOrders.isEmpty()) {
            Toast.makeText(this, "You haven't available orders", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayAllOrders(OrderAdapter adapter) {
        displayedOrders = orders;
        adapter.updateRecords(displayedOrders, courier);
    }

    private void showInfo(double cost) {
        Toast.makeText(this, "Total: " + cost, Toast.LENGTH_LONG).show();
    }

    private void setCourierInfo(Courier courier) {
        TextView courierName = findViewById(R.id.courier_name);
        courierName.setText(courier.getName());

        TextView courierPaymentAccount = findViewById(R.id.courier_paymentAccount);
        courierPaymentAccount.setText(courier.getPaymentAccount());

        updateCourierAbilitiesTextView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_courier_abilities, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_courier_abilities) {
            showPopupMenu(item.getActionView());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopupMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(this, anchorView);
        popupMenu.inflate(R.menu.menu_courier_abilities);

        Menu menu = popupMenu.getMenu();
        for (CourierAbility ability : CourierAbility.values()) {
            menu.add(Menu.NONE, ability.ordinal(), Menu.NONE, ability.toString());
        }

        popupMenu.setOnMenuItemClickListener(item -> {
            item.setChecked(!item.isChecked());
            int itemId = item.getItemId();
            CourierAbility selectedAbility = CourierAbility.values()[itemId];
            if (item.isChecked()) {
                selectedAbilities.add(selectedAbility);
            } else {
                selectedAbilities.remove(selectedAbility);
            }
            updateCourierAbilitiesTextView();
            return true;
        });

        popupMenu.show();
    }

    private void updateCourierAbilitiesTextView() {
        TextView courierAbilities = findViewById(R.id.courier_abilities);
        courierAbilities.setText(selectedAbilities.toString());
    }


    //TODO abilities enum. Умею работать с документом, с хрупким
    //TODO menu справа сверху checkbox с выбором abilities
    //TODO исчезание после выбора и accept

}
