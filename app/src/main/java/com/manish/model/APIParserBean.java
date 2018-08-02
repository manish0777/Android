package com.manish.model;

import com.manish.ApplicationLoader;
import com.manish.MainActivity;
import com.manish.db.DatabaseInterface;
import com.manish.db.EcomDataBase;
import com.manish.db.table.Category;
import com.manish.db.table.MostOrderdProduct;
import com.manish.db.table.MostSharedProduct;
import com.manish.db.table.MostViewedProduct;
import com.manish.db.table.Product;
import com.manish.db.table.ProductVarient;
import com.manish.db.table.SubCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class APIParserBean {


    public static synchronized void parseData(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray catArr = jsonObject.getJSONArray("categories");
            JSONArray mostviewdAA = jsonObject.getJSONArray("rankings");
            processCategory(catArr);
            processRankingData(mostviewdAA);
        } catch (Exception e) {

        }
    }

    private static void processRankingData(JSONArray mostviewdAA) {
        try {
            if(mostviewdAA!=null && mostviewdAA.length()>0) {
                for (int i = 0; i < mostviewdAA.length(); i++) {
                    JSONObject rankingobj = mostviewdAA.getJSONObject(i);
                    String title = rankingobj.getString("ranking");
                    if (title != null && title.equalsIgnoreCase("Most Viewed Products")) {
                        JSONArray viewedProd = rankingobj.getJSONArray("products");
                        getMostViewdProduct(viewedProd);
                    }
                    if (title != null && title.equalsIgnoreCase("Most OrdeRed Products")) {
                        JSONArray viewedProd = rankingobj.getJSONArray("products");
                        getMostOrderedProduct(viewedProd);

                    }
                    if (title != null && title.equalsIgnoreCase("Most ShaRed Products")) {
                        JSONArray viewedProd = rankingobj.getJSONArray("products");
                        getMostSharedProduct(viewedProd);
                    }
                }

            }
        } catch (Exception e) {

        }
    }

    private static void getMostSharedProduct(JSONArray viewedProd) {
        try {
            List<MostSharedProduct> mostViewedProducts = new ArrayList<>();
            for (int i = 0; i < viewedProd.length(); i++) {
                JSONObject jsonObject = viewedProd.getJSONObject(i);
                MostSharedProduct mostViewedProduct = new MostSharedProduct();
                mostViewedProduct.setId(Integer.parseInt(jsonObject.getString("id")));
                mostViewedProduct.setMostShared(jsonObject.getString("shares"));
                mostViewedProducts.add(mostViewedProduct);
            }
            EcomDataBase.getEcomDataBase(ApplicationLoader.getInstance()).getDatabaseInterface().saveMostSharedProduct(mostViewedProducts);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void getMostOrderedProduct(JSONArray viewedProd) {
        try {
            List<MostOrderdProduct> mostViewedProducts = new ArrayList<>();
            for (int i = 0; i < viewedProd.length(); i++) {
                JSONObject jsonObject = viewedProd.getJSONObject(i);
                MostOrderdProduct mostViewedProduct = new MostOrderdProduct();
                mostViewedProduct.setId(Integer.parseInt(jsonObject.getString("id")));
                mostViewedProduct.setOrderCount(jsonObject.getString("order_count"));
                mostViewedProducts.add(mostViewedProduct);
            }
            EcomDataBase.getEcomDataBase(ApplicationLoader.getInstance()).getDatabaseInterface().saveMostOrderedProduct(mostViewedProducts);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void getMostViewdProduct(JSONArray viewedProd) {
        try {
            List<MostViewedProduct> mostViewedProducts = new ArrayList<>();
            for (int i = 0; i < viewedProd.length(); i++) {
                JSONObject jsonObject = viewedProd.getJSONObject(i);
                MostViewedProduct mostViewedProduct = new MostViewedProduct();
                mostViewedProduct.setId(Integer.parseInt(jsonObject.getString("id")));
                mostViewedProduct.setViewcount(jsonObject.getString("view_count"));
                mostViewedProducts.add(mostViewedProduct);
            }
            EcomDataBase.getEcomDataBase(ApplicationLoader.getInstance()).getDatabaseInterface().saveMostViewedProduct(mostViewedProducts);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private static void processCategory(JSONArray catArr) {
        try {
            List<Category> categoryArrayList = new ArrayList<>();
            for (int i = 0; i < catArr.length(); i++) {
                JSONObject catobject = catArr.getJSONObject(i);
                Category category = new Category();
                category.setCatId(Integer.parseInt(catobject.getString("id")));
                category.setMappingId(Integer.parseInt(catobject.getString("id")));

                JSONArray subcatArr=catobject.getJSONArray("child_categories");
                if(subcatArr!=null && subcatArr.length()>0) {
                   processSubCategory(subcatArr,category,categoryArrayList);
                }
                    category.setCatName(catobject.getString("name"));
                    category.setParent(0);
                    categoryArrayList.add(category);

//                categoryArrayList.add(category);
                JSONArray prodArr = catobject.getJSONArray("products");
                if(prodArr!=null&& prodArr.length()>0) {
                    processProduct(prodArr, Integer.parseInt(catobject.getString("id")));
                }
            }
            EcomDataBase.getEcomDataBase(ApplicationLoader.getInstance()).getDatabaseInterface().saveAllCategory(categoryArrayList);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private static void processSubCategory(JSONArray subcatArr, Category category, List<Category> categoryArrayList) {
        try {
            ArrayList<Category> categories= new ArrayList<>();
            for (int j = 0; j <subcatArr.length(); j++) {
                Category category1=new Category();
                int value=subcatArr.getInt(j);
                category1.setParent(category.getCatId());
                category1.setCatName(""+value);
//                category1.setCatId(category.getCatId());
                category1.setMappingId(category.getCatId());
                categories.add(category1);
            }
            categoryArrayList.addAll(categories);
        } catch (Exception e) {

        }


    }

    private static void processProduct(JSONArray prodArr, int catId) {
        try {
            List<Product> productArrayList = new ArrayList<>();
            for (int i = 0; i < prodArr.length(); i++) {
                JSONObject prodobject = prodArr.getJSONObject(i);
                Product product = new Product();
                product.setPId(Integer.parseInt(prodobject.getString("id")));
                product.setPName(prodobject.getString("name"));
                product.setCatgoryID(catId);
                productArrayList.add(product);
                JSONArray varientArr = prodobject.getJSONArray("variants");
                if(varientArr!=null && varientArr.length()>0) {
                    processVarient(varientArr, product.getPId());
                }
            }
            EcomDataBase.getEcomDataBase(ApplicationLoader.getInstance()).getDatabaseInterface().saveAllProduct(productArrayList);

        } catch (Exception e) {

        }
    }

    private static void processVarient(JSONArray varientArr, Integer pId) {
        try {
            List<ProductVarient> productArrayList = new ArrayList<>();
            for (int i = 0; i < varientArr.length(); i++) {
                JSONObject varObj = varientArr.getJSONObject(i);
                Iterator<String> keys = varObj.keys();
                String key="";
                while (keys.hasNext()) {
                    ProductVarient product = new ProductVarient();
                    product.setProductId(pId);
                    key = keys.next();
                    if(!key.equalsIgnoreCase("id")) {
                        product.setVType(key);
                        product.setVValue(varObj.getString(key));
                        productArrayList.add(product);
                    }
                }
            }
            EcomDataBase.getEcomDataBase(ApplicationLoader.getInstance()).getDatabaseInterface().saveProductVarient(productArrayList);

        } catch (Exception e) {

        }
    }


}
