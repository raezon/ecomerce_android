package in.macrocodes.ecomerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.macrocodes.ecomerce.database.Product;

public class UserProductActivity extends AppCompatActivity {

    private RecyclerView recyclerViewProducts;
    private TextView labelLogout;

    private View login;
    private View register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //R.layout.
        setContentView(R.layout.activity_user_product);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        labelLogout=findViewById(R.id.logoutButton);


        labelLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(UserProductActivity.this,Main2Activity.class);
            startActivity(intent);
            finish();
            }
        });

        // Assuming you have a list of products
        List<Product> productList = getProductList();

        // Set up RecyclerView adapter directly
        recyclerViewProducts.setAdapter(new RecyclerView.Adapter<ProductViewHolder>() {
            @Override
            public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
                return new ProductViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ProductViewHolder holder, int position) {
                Product product = productList.get(position);
                holder.productNameTextView.setText(product.getName());
                holder.productCategoryTextView.setText(product.getMark());
                holder.productPriceTextView.setText("$" + product.getPrice());
            }

            @Override
            public int getItemCount() {
                return productList.size();
            }
        });
    }

    private List<Product> getProductList() {
        // Implement your logic to fetch products here
        // For now, let's create some dummy products
        List<Product> dummyProducts = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dummyProducts.add(new Product("Product " + (i + 1), "Category", 10.00));
        }
        return dummyProducts;
    }

    // ViewHolder for each product item
    private static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextView;
        private TextView productCategoryTextView;
        private TextView productPriceTextView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.textViewProductName);
            productCategoryTextView = itemView.findViewById(R.id.textViewProductCategory);
            productPriceTextView = itemView.findViewById(R.id.textViewProductPrice);
        }
    }
}
