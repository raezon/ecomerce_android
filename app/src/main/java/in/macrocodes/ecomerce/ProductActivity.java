package in.macrocodes.ecomerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import in.macrocodes.ecomerce.database.MyDatabase;
import in.macrocodes.ecomerce.database.Product;
import in.macrocodes.ecomerce.database.ProductDao;

public class ProductActivity extends AppCompatActivity {

    private EditText editTextProductName;
    private EditText editTextProductMark;
    private EditText editTextProductPrice;
    private Button buttonAddProduct;
    private Button buttonSyncProducts;
    private ListView listViewProducts;
    private TextView logout;

    private MyDatabase database;
    private ProductDao productDao;

    private ArrayAdapter<String> productAdapter;

    // Executor service for running database operations in the background
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductMark = findViewById(R.id.editTextProductMark);
        editTextProductPrice = findViewById(R.id.editTextProductPrice);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        buttonSyncProducts = findViewById(R.id.buttonSyncProducts);
        listViewProducts = findViewById(R.id.listViewProducts);
        logout=findViewById(R.id.logoutButton);

        // Initialize the database and DAO
        database = MyDatabase.getInstance(this);
        productDao = database.productDao();

        productAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewProducts.setAdapter(productAdapter);

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        // Display all products when the activity is created
         displayAllProducts();

         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent =new Intent(ProductActivity.this,UserProductActivity.class);
                 startActivity(intent);
                 finish();
             }
         });
    }

    private void addProduct() {
        final String productName = editTextProductName.getText().toString().trim();
        final String productMark = editTextProductMark.getText().toString().trim();
        final double productPrice = Double.parseDouble(editTextProductPrice.getText().toString().trim());

        if (!productName.isEmpty() && !productMark.isEmpty() && productPrice > 0) {

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    // Perform database operation on background thread
                    Product newProduct = new Product(productName,productMark,productPrice);
                    //newProduct.setName(productName);
                    //newProduct.setMark(productMark);
                    //newProduct.setPrice(productPrice);
                    productDao.insert(newProduct);

                    // Update UI on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            productAdapter.add(productName);
                            editTextProductName.getText().clear();
                            editTextProductMark.getText().clear();
                            editTextProductPrice.getText().clear();
                        }
                    });
                }
            });
        } else {
            Toast.makeText(this, "Please fill all the fields correctly", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayAllProducts() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Fetch all products from the database
                final List<Product> products = productDao.getAllProducts();

                // Update UI on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        productAdapter.clear();
                        for (Product product : products) {
                            productAdapter.add(product.getName());
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Shutdown the executor service when the activity is destroyed
        executorService.shutdown();
    }


}
