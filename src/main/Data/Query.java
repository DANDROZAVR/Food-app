package main.Data;

import javafx.util.Pair;
import main.Model.Others.TimeInterval;
import main.Model.Products.Drinks;
import main.Model.Products.Product;
import main.Model.Products.Species;
import main.Model.Recipes.Recipe;
import main.Model.Restaurants.Restaurant;
import main.Model.Shops.Shop;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class Query {
    public static ArrayList<ArrayList<String>> getFullInformation(String fromTable, int[] order) throws SQLException {
        String query = new String("SELECT * FROM " + fromTable + orderQuery(order));
        return Database.execute(query);
    }
    public static String orderQuery(int[] order) {
        StringBuilder query = new StringBuilder();
        if (order.length > 0) {
            query.append("ORDER BY ");
            for (int i = 0; i < order.length; ++i) {
                if (i > 0)
                    query.append(",");
                query.append(order[i]);
            }
        }
        return query.toString();
    }
    public static ArrayList<ArrayList<String>> getFullInformation(String fromTable) throws SQLException {
        String query = new String("SELECT * FROM " + fromTable + ";");
        return Database.execute(query);
    }
    public static ArrayList<Pair<Integer,Integer>> getAllContentOfRecipe(int id) throws SQLException {
        ArrayList<ArrayList<String>> content = Database.execute("select id, id_rec from recipes_content_products where id_rec = " + id + " union (select id, id_rec from recipes_content_recipes where id_rec = " + id + ");");
        ArrayList<Pair<Integer,Integer>> trueContent = new ArrayList<>();
        for(int i = 1; i < content.size(); i++){
            trueContent.add(new Pair<Integer, Integer>(Integer.parseInt(content.get(i).get(0)), Integer.parseInt(content.get(i).get(1))));
            System.out.println(trueContent.get(i - 1));
        }
        return trueContent;
        /*
        ArrayList<ArrayList<String>> content = Database.execute("select getRecipeContentRecipes(" + id+ ");");
        content.addAll(Database.execute("select getRecipeContentProducts(" + id+ ");"));
        ArrayList<Pair<Integer,Integer>> trueContent = new ArrayList<>();
        for(ArrayList<String> s: content){
            if(s.get(0) == null || s.get(0).equals("getrecipecontentrecipes") || s.get(0).equals("getrecipecontentproducts")){
                continue;
            }
            int i=0;
                while(!(s.get(0).charAt(i)=='}' && s.get(0).charAt(i+1)=='}')) {
                    if(s.get(0).charAt(i+1)==','){
                        i+=3;
                    }else {
                        i += 2;
                    }
                    StringBuilder id1 = new StringBuilder();
                    StringBuilder weight1 = new StringBuilder();
                    while (s.get(0).charAt(i) != ',') {
                        id1.append(s.get(0).charAt(i));
                        i++;
                    }
                    i++;
                    while (s.get(0).charAt(i) != ',') {
                        weight1.append(s.get(0).charAt(i));
                        i++;
                    }
                    if(s.get(0).charAt(i+1) == 'g'){
                        i+=2;
                    }else{
                        i+=3;
                    }
                    trueContent.add(new Pair<Integer, Integer>(Integer.parseInt(id1.toString()), Integer.parseInt(weight1.toString())));
                }
        }
        return trueContent;
        */
    }
    public static ArrayList<Product> getSimpleProductsWithoutSpecies() throws Exception {
        String query = new String("SELECT * FROM products;");
        return Parser.getProductsFrom(Database.execute(query));
    }
    public static ArrayList<Product> getSimpleProductsWithSpecies() throws Exception {
        String query = new String("SELECT * FROM products left join species_taste;");
        return Parser.getProductsFrom(Database.execute(query));
    }
    public static String resultToString(ArrayList<ArrayList<String>> arr) {
        StringBuilder res = new StringBuilder();
        for (int i = 1; i < arr.size(); ++i) {
            for (String j : arr.get(i))
                res.append(j).append(" ");
            res.append('\n');
        }
        return res.toString();
    }
    public static String getTagsById(int id) throws Exception {
        //String query = new String("select area from products_areatag where id_prod = " + id);
        String query2 = new String("select tag from products_tag where id_prod = " + id);
        return resultToString(Database.execute(query2));
    }
    public static String getTagsByIdRecipes(int id) throws Exception {
        //String query = new String("select area from recipes_areatag where id = " + id);
        String query2 = new String("select tag from recipes_tag where id = " + id);
        return resultToString(Database.execute(query2));
    }
    public static int getCaloriesFromProducts(String fromTable, int item) throws SQLException {
        String query = "SELECT calories FROM " + fromTable + " WHERE id_prod=" + String.valueOf(item) + ";";
        return Integer.parseInt(Database.execute(query).get(1).get(0));
    }
    public static int getCaloriesFromRecipes(String fromTable, int item) throws SQLException {
        String query = "SELECT calories FROM " + fromTable + " WHERE id_rec=" + String.valueOf(item) + ";";
        return Integer.parseInt(Database.execute(query).get(1).get(0));
    }
    public static ArrayList<ArrayList<String>> getByName(String fromTable, String name) throws SQLException {
        String query = new String("SELECT * FROM " + fromTable + " where name = '" + name + "';");
        return Database.execute(query);
    }
    public static ArrayList<ArrayList<String>> getByNamePrefix(String fromTable, String prefixName) throws SQLException {
        String query = new String("SELECT * FROM " + fromTable + " where name like '" + prefixName + "%';");
        return Database.execute(query);
    }
    public static ArrayList<ArrayList<String>> getByNamePrefix_withSpecies(String fromTable, String prefixName) throws SQLException {
        String query = new String("SELECT * from (SELECT * FROM " + fromTable + " where name like '" + prefixName + "%') as products1 " +
                            "left join species_taste using(id_prod);");
        System.out.println(query);
        return Database.execute(query);
    }
    public static ArrayList<ArrayList<String>> getByNamePrefix_withDrinks(String fromTable, String prefixName) throws SQLException {
        String query = new String("SELECT * from (SELECT * FROM " + fromTable + " where name like '" + prefixName + "%') as products1 " +
                            "left join drinks_info using(id_prod);");
        System.out.println(query);
        return Database.execute(query);
    }
    public static ArrayList<ArrayList<String>> getByNamePrefix_all(String fromTable, String prefixName) throws SQLException {
        String query = new String(
                        "SELECT * from (SELECT * FROM " + fromTable + " where name like '" + prefixName + "%') as products1 " +
                                "left join products_tag using(id_prod)"
                             + " left join drinks_info using(id_prod)" +
                                "left join products_nutrient using(id_prod);");
        return Database.execute(query);
    }
    public static ArrayList<ArrayList<String>> getByNamePrefix_all_first(String fromTable, String prefixName) throws SQLException {
        String query = new String(
                "SELECT * from (SELECT * FROM " + fromTable + " where name like '" + prefixName + "%') as products1 " +
                        "left join species_taste using(id_prod)"
                        + " left join drinks_info using(id_prod) limit 1;");
        return Database.execute(query);
    }
    public static int getNewIdFor(String S) throws SQLException {
        if (S.equals("products")) {
            System.out.println(Database.execute("select nextval('for_id_products');").get(1).get(0));
            return Integer.parseInt(Database.execute("select nextval('for_id_products');").get(1).get(0));
        }
        if (S.equals("recipes")) {
            return Integer.parseInt(Database.execute("select nextval('for_id_recipes');").get(1).get(0));
        }
        if (S.equals("shops")) {
            return Integer.parseInt(Database.execute("select nextval('for_id_shop');").get(1).get(0));
        }
        if (S.equals("restaurants")) {
            return Integer.parseInt(Database.execute("select nextval('for_id_restaurants');").get(1).get(0));
        }
        throw new SQLException("unknown val-sequence:" + S);
    }

    public static void addNewProduct(Product p) throws SQLException, ClassNotFoundException, Exception {
        if(Integer.parseInt(Database.execute(new String("select count(*) from products where id_prod = " + p.getId() + ";")).get(1).get(0)) != 0){
            throw new Exception("has this id");
        }
        if(Integer.parseInt(Database.execute(new String("select count(*) from products where name = '" + p.getName() + "';")).get(1).get(0)) != 0){
            throw new Exception("has this name");
        }
        String query = new String("INSERT INTO products(id_prod, product_group, product_class, name, description, calories) VALUES ("
                + p.getId()
                + ", '"
                + p.getProductType()
                +"', '"
                + Parser.getProductClass(p.getClass())
                +"', '"
                + p.getName()
                +"', '"
                +p.getDescription()
                +"', "
                +p.getCalories()
                +");");
        try{
            Database.update(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        query = new String("select count(*) from products where " +
                "id_prod = " + p.getId() + " AND " +
                "product_group = '" + p.getProductType() + "' AND " +
                "product_class = '" + Parser.getProductClass(p.getClass())+ "' AND " +
                "name = '" + p.getName() + "' AND " +
                "description = '" + p.getDescription() + "' AND " +
                "calories = " + p.getCalories() + ";"
        );
        if(Integer.parseInt(Database.execute(query).get(1).get(0)) == 0){
            throw new Exception("can't add to products");
        }
        if(Parser.getProductClass(p.getClass()).equals("Solids")){

        }
        if(Parser.getProductClass(p.getClass()).equals("Species")){
            try{
                query = new String("INSERT INTO species_taste(id_prod, taste) VALUES("
                +p.getId()
                +", '"
                +((Species)p).getTaste()
                +"');"
                );
                Database.update(query);
            } catch (Exception e){
                e.printStackTrace();
                query = new String("delete from products where id_prod = " + p.getId() + ";");
                Database.update(query);
                throw new Exception("can't add to Species");
            }
        }
        if(Parser.getProductClass(p.getClass()).equals("Drinks")){
            try{
                query = new String("INSERT INTO drinks_info(id_prod, sugar, colour) VALUES("
                        +p.getId()
                        +", "
                        +((Drinks)p).hasSugar()
                        +", "
                        +((Drinks)p).getColour()
                        +");"
                );
                Database.update(query);
            } catch (Exception e){
                e.printStackTrace();
                query = new String("delete from products where id_prod = " + p.getId() + ";");
                Database.update(query);
                throw new Exception("can't add to Drinks");
            }
        }
        /*
        query = new String("INSERT INTO products_areatag(id_prod, area) VALUES ("
                + Id
                + ", '"
                + area
                + "');"
        );
        Database.update(query);
        */
    }

    public static void addNewRecipe(Recipe p, ArrayList<Pair<Recipe, Integer>> list_of_recipes, ArrayList<Pair<Product, Integer>> list_of_products) throws SQLException, Exception {
        if(Integer.parseInt(Database.execute(new String("select count(*) from recipes where id_rec = " + p.getId() + ";")).get(1).get(0)) != 0){
            throw new Exception("has this id");
        }
        if(Integer.parseInt(Database.execute(new String("select count(*) from recipes where name = '" + p.getName() + "';")).get(1).get(0)) != 0){
            throw new Exception("has this name");
        }
        String query = new String("INSERT INTO recipes(id_rec, name, prep_time, calories, description, instruction) VALUES ("
                + p.getId()
                + ", '"
                + p.getName()
                +"', "
                + p.getTime()
                +", "
                + p.getAllCalories()
                +", '"
                +p.getDescription()
                +"', '"
                +p.getInstruction()
                +"');");
        try{
            Database.update(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        query = new String("select count(*) from recipes where " +
                "id_rec = " + p.getId() + " AND " +
                "name = '" + p.getName() + "' AND " +
                "prep_time = " + p.getTime() + " AND " +
                "calories = " + p.getAllCalories() + " AND " +
                "description = '" + p.getDescription() + "' AND " +
                "instruction = '" + p.getInstruction() + "';"
        );
        if(Integer.parseInt(Database.execute(query).get(1).get(0)) == 0){
            throw new Exception("can't add to recipes");
        }

        try{
            boolean is_all_good = true;
            for(Pair<Recipe, Integer> i : list_of_recipes){
                if(Integer.parseInt(Database.execute(new String("select count(*) from recipes where id_rec=" + i.getKey().getId() + ";")).get(1).get(0)) == 0){
                    is_all_good = false;
                    break;
                }
                String qry = new String("insert into recipes_content_recipes(id_rec, id, weight, weight_type) values(" + p.getId() + "," + i.getKey().getId() + "," + i.getValue() + ",'g');");
                Database.update(qry);
                if(Integer.parseInt(Database.execute(new String(
                                                            "select count(*) from recipes_content_recipes where id_rec="
                                                                    + p.getId()
                                                                    + " AND id="
                                                                    + i.getKey().getId()
                                                                    + "AND weight="
                                                                    + i.getValue()
                                                                    + "AND weight_type='g'"
                                                                    + ";")).get(1).get(0)) == 0){
                    is_all_good = false;
                    break;
                }
            }
            for(Pair<Product, Integer> i : list_of_products){
                if(Integer.parseInt(Database.execute(new String("select count(*) from products where id_prod=" + i.getKey().getId() + ";")).get(1).get(0)) == 0){
                    is_all_good = false;
                    break;
                }
                String qry = new String("insert into recipes_content_products(id_rec, id, weight, weight_type) values(" + p.getId() + "," + i.getKey().getId() + "," + i.getValue() + ",'g');");
                Database.update(qry);
                if(Integer.parseInt(Database.execute(new String(
                        "select count(*) from recipes_content_products where id_rec="
                                + p.getId()
                                + " AND id="
                                + i.getKey().getId()
                                + " AND weight="
                                + i.getValue()
                                + " AND weight_type='g'"
                                + ";")).get(1).get(0)) == 0){
                    is_all_good = false;
                    break;
                }
            }
            if(!is_all_good) {
                Database.update(new String("delete from recipes_content_recipes where id_rec=" + p.getId() + ";"));
                Database.update(new String("delete from recipes_content_products where id_rec=" + p.getId() + ";"));
                Database.update(new String("delete from recipes where id_rec=" + p.getId() + ";"));
                throw new Exception("Something bad with products or recipes from list");
            }
        }catch (Exception e){
            e.printStackTrace();
            Database.update(new String("delete from recipes_content_recipes where id_rec=" + p.getId() + ";"));
            Database.update(new String("delete from recipes_content_products where id_rec=" + p.getId() + ";"));
            Database.update(new String("delete from recipes where id_rec=" + p.getId() + ";"));
            throw new Exception("Something bad with products or recipes from list");
        }

    }

    public static void addNewRestaurant(Restaurant r, String description, boolean isFood, int stars, TimeInterval weekday, TimeInterval saturday, TimeInterval sunday) throws SQLException, Exception {
        if(Integer.parseInt(Database.execute(new String("select count(*) from restaurants_main where id = " + r.getId() + ";")).get(1).get(0)) != 0){
            throw new Exception("has this id");
        }
        if(Integer.parseInt(Database.execute(new String("select count(*) from restaurants_main where name = '" + r.getName() + "';")).get(1).get(0)) != 0){
            throw new Exception("has this name");
        }

        String query = new String("INSERT INTO restaurants_main(id, name, address, geoposition, open_weekdays, close_weekdays, open_saturday, close_saturday, open_sunday, close_sunday, stars, description, food_delivery) VALUES ("
                + r.getId()
                + ", '"
                + r.getName()
                +"', '"
                + r.getAdres()
                +"', '"
                + r.getGeoposition()
                +"', '"
                + weekday.getStartHour()
                + ":"
                + weekday.getStartMinute()
                + "', '"
                + weekday.getEndHour()
                + ":"
                + weekday.getEndMinute()

                +"', '"
                + saturday.getStartHour()
                + ":"
                + saturday.getStartMinute()
                + "', '"
                + saturday.getEndHour()
                + ":"
                + saturday.getEndMinute()
                +"', '"
                + sunday.getStartHour()
                + ":"
                + sunday.getStartMinute()
                + "', '"
                + sunday.getEndHour()
                + ":"
                + sunday.getEndMinute()
                + "', "
                + stars
                + ", '"
                + description
                + "', "
                + isFood
                +");");
        try{
            Database.update(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        query = new String("select count(*) from restaurants_main where " +
                "id = " + r.getId() + " AND " +
                "name = '" + r.getName() + "' AND " +
                "geoposition ~= '" + r.getGeoposition() + "' AND " +
                "address = '" + r.getAdres()
                + "' AND open_weekdays='" +weekday.getStartHour() + ":" + weekday.getStartMinute()
                + "' AND close_weekdays='" + weekday.getEndHour() + ":" + weekday.getEndMinute()
                + "' AND open_saturday='" +saturday.getStartHour() + ":" + saturday.getStartMinute()
                + "' AND close_saturday='" + saturday.getEndHour() + ":" + saturday.getEndMinute()
                + "' AND open_sunday='" +sunday.getStartHour() + ":" + sunday.getStartMinute()
                + "' AND close_sunday='" + sunday.getEndHour() + ":" + sunday.getEndMinute()
                +"' AND stars=" + stars
                + " AND description='" + description + "' AND food_delivery=" + isFood
                + ";"
        );
        if(Integer.parseInt(Database.execute(query).get(1).get(0)) == 0){
            throw new Exception("can't add to restaurants_main");
        }

    }

    public static void addNewShop(Shop r, String description, boolean isFood, int stars, TimeInterval weekday, TimeInterval saturday, TimeInterval sunday) throws SQLException, Exception {
        if(Integer.parseInt(Database.execute(new String("select count(*) from shops_main where id = " + r.getId() + ";")).get(1).get(0)) != 0){
            throw new Exception("has this id");
        }
        if(Integer.parseInt(Database.execute(new String("select count(*) from shops_main where name = '" + r.getName() + "';")).get(1).get(0)) != 0){
            throw new Exception("has this name");
        }

        String query = new String("INSERT INTO shops_main(id, name, address, geoposition, open_weekdays, close_weekdays, open_saturday, close_saturday, open_sunday, close_sunday, stars, description, food_delivery) VALUES ("
                + r.getId()
                + ", '"
                + r.getName()
                +"', '"
                + r.getAdres()
                +"', '"
                + r.getGeoposition()
                +"', '"
                + weekday.getStartHour()
                + ":"
                + weekday.getStartMinute()
                + "', '"
                + weekday.getEndHour()
                + ":"
                + weekday.getEndMinute()

                +"', '"
                + saturday.getStartHour()
                + ":"
                + saturday.getStartMinute()
                + "', '"
                + saturday.getEndHour()
                + ":"
                + saturday.getEndMinute()
                +"', '"
                + sunday.getStartHour()
                + ":"
                + sunday.getStartMinute()
                + "', '"
                + sunday.getEndHour()
                + ":"
                + sunday.getEndMinute()
                + "', "
                + stars
                + ", '"
                + description
                + "', "
                + isFood
                +");");
        try{
            Database.update(query);
        }catch (Exception e){
            e.printStackTrace();
        }

        query = new String("select count(*) from shops_main where " +
                "id = " + r.getId() + " AND " +
                "name = '" + r.getName() + "' AND " +
                "geoposition ~= '" + r.getGeoposition() + "' AND " +
                "address = '" + r.getAdres()
                + "' AND open_weekdays='" +weekday.getStartHour() + ":" + weekday.getStartMinute()
                + "' AND close_weekdays='" + weekday.getEndHour() + ":" + weekday.getEndMinute()
                + "' AND open_saturday='" +saturday.getStartHour() + ":" + saturday.getStartMinute()
                + "' AND close_saturday='" + saturday.getEndHour() + ":" + saturday.getEndMinute()
                + "' AND open_sunday='" +sunday.getStartHour() + ":" + sunday.getStartMinute()
                + "' AND close_sunday='" + sunday.getEndHour() + ":" + sunday.getEndMinute()
                +"' AND stars=" + stars
                + " AND description='" + description + "' AND food_delivery=" + isFood
                + ";"
        );
        if(Integer.parseInt(Database.execute(query).get(1).get(0)) == 0){
            throw new Exception("can't add to shops_main");
        }

    }
}
