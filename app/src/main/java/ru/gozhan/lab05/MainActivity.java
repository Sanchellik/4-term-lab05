package ru.gozhan.lab05;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Iterator;

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
    private OrderAdapter adapter;

    private Dialog takenOrdersDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        courier = new Courier(
                "Gozhan Alexandr",
                "88005353535",
                new ArrayList<>());

        setCourierInfo();

        ArrayList<Company> companies = (ArrayList<Company>) CreateAllObjects.createCompanies();
        ArrayList<Package> packages = (ArrayList<Package>) CreateAllObjects.createPackages();
        orders = (ArrayList<Order>) CreateAllObjects.createOrders(companies, packages);

        adapter = new OrderAdapter(this, orders, courier);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Button btn_show_all_orders = findViewById(R.id.button_show_all);
        Button button_show_available = findViewById(R.id.button_show_available);
        Button btn_ok = findViewById(R.id.button_ok);
        Button btn_clear = findViewById(R.id.button_clear);
        Button btn_show_taken = findViewById(R.id.button_show_taken);

        btn_show_taken.setOnClickListener(v -> showTakenOrders());

        btn_show_all_orders.setOnClickListener(v -> displayAllOrders());

        button_show_available.setOnClickListener(v -> displayAvailableOrders());

        btn_ok.setOnClickListener(v -> {
            double totalCash = 0;
            for (int i = 0; i < adapter.getOrders().size(); i++) {
                if (adapter.getOrders().get(i).isSelected()) {
                    courier.addOrder(adapter.getOrders().get(i));
                    totalCash += (adapter.getOrders().get(i).getPrice());
                }
            }
            Iterator<Order> iterator = orders.iterator();
            while (iterator.hasNext()) {
                Order order = iterator.next();
                // Условие для удаления элемента
                if (order.isSelected()) {
                    iterator.remove(); // Удаление элемента через итератор
                }
            }
            showTotalInfo(totalCash);
            setCourierInfo();
            adapter.setOrders(orders);
            displayAvailableOrders();
        });

        btn_clear.setOnClickListener(v -> {
            for (int i = 0; i < adapter.getOrders().size(); i++) {
                adapter.getOrders().get(i).setSelected(false);
            }
            adapter.notifyDataSetChanged();
        });

        displayAvailableOrders();
    }

    private void showTakenOrders() {
        takenOrdersDialog = new Dialog(this);
        takenOrdersDialog.setContentView(R.layout.dialog_taken_orders);

        TextView takenOrdersText = takenOrdersDialog.findViewById(R.id.taken_orders_text);
        Button cancelOrdersButton = takenOrdersDialog.findViewById(R.id.cancel_orders_button);

        // Добавьте здесь код для отображения взятых заказов
        String takenOrdersInfo = "";
        for (Order order : courier.getOrders()) {
            takenOrdersInfo += order.toString() + "\n";
        }
        takenOrdersText.setText(takenOrdersInfo);

        cancelOrdersButton.setOnClickListener(v -> cancelAllOrders());

        takenOrdersDialog.show();
    }

    private void cancelAllOrders() {
        orders.addAll(courier.getOrders());
        courier.getOrders().clear();
        takenOrdersDialog.dismiss();
        displayAvailableOrders();
        setCourierInfo();
    }

    private void displayAvailableOrders() {
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

    private void displayAllOrders() {
        displayedOrders = orders;
        adapter.updateRecords(displayedOrders, courier);
    }

    private void showTotalInfo(double cost) {
        Toast.makeText(this, "Total: " + cost, Toast.LENGTH_LONG).show();
    }

    private void setCourierInfo() {
        TextView courierName = findViewById(R.id.courier_name);
        courierName.setText(courier.getName());

        TextView courierPaymentAccount = findViewById(R.id.courier_paymentAccount);
        courierPaymentAccount.setText(courier.getPaymentAccount());

        TextView courierAbilities = findViewById(R.id.courier_abilities);
        courierAbilities.setText(courier.getAbilities().toString());

        TextView courierNumberOfOrders = findViewById(R.id.courier_number_of_orders);
        courierNumberOfOrders.setText(String.valueOf(courier.getOrders().size()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Загружаем меню
        getMenuInflater().inflate(R.menu.menu_courier_abilities, menu);

        // Получаем группу элементов меню
        MenuItem group = menu.findItem(R.id.group_courier_abilities);
        // Проходимся по всем возможным способностям курьера и добавляем их в меню
        for (CourierAbility ability : CourierAbility.values()) {
            Intent intent = new Intent();
            intent.putExtra("ability", ability.name());
            group.getSubMenu().add(Menu.NONE, Menu.NONE, Menu.NONE, ability.name())
                    .setIntent(intent)
                    .setCheckable(true)
                    .setChecked(courier.hasAbility(ability));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = item.getIntent();
        if (intent != null) {
            String selectedAbilityName = intent.getStringExtra("ability");
            if (selectedAbilityName != null) {
                CourierAbility selectedAbility = CourierAbility.valueOf(selectedAbilityName);

                // Если способность уже есть у курьера, удаляем её, иначе - добавляем
                if (courier.hasAbility(selectedAbility)) {
                    courier.getAbilities().remove(selectedAbility);
                } else {
                    courier.getAbilities().add(selectedAbility);
                }

                // Меняем состояние выбранного элемента меню
                item.setChecked(!item.isChecked());

                setCourierInfo();

                // Обновляем отображаемые заказы
                displayAvailableOrders();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    //TODO abilities enum. Умею работать с документом, с хрупким
    //TODO menu справа сверху checkbox с выбором abilities
    //TODO исчезание после выбора и accept

}
