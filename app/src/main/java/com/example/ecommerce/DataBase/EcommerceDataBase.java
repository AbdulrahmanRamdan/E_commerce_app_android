package com.example.ecommerce.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ecommerce.Models.Customer;

public class EcommerceDataBase extends SQLiteOpenHelper {
    private static String dbname ="EcommerceDB1";
    SQLiteDatabase EcommerceDB;

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    public EcommerceDataBase(@Nullable Context context) {

        super(context, dbname, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table Customers(CustID integer primary key autoincrement, Custname text, Username text, Password text, Gender text, Birthdate text, Job text , Question text , Answer text)");

        db.execSQL("Create table Categories(CatID integer primary key autoincrement, Catname text)");


        db.execSQL("Create table Orders(OrdID integer primary key autoincrement, OrdDate text, CustID integer, Address text," +
                " foreign key(CustID) references Customers(CustID))");


        db.execSQL("Create table Products(ProID integer primary key autoincrement, ProName text, Price integer, Quantity integer, CatID integer," +
                " foreign key(CatID) references Categories(CatID))");


        db.execSQL("Create table OrderDetails(OrdID integer not null , Quantity integer , ProID integer not null , primary key(OrdID,ProID) ," +
                " foreign key(OrdID) references Orders(OrdID) , foreign key(ProID) references Products(ProID))");
        db.execSQL("Create table pictures(bitmap text,pro_id integer,catid integer,foreign key(pro_id) references Products(ProID),foreign key(catid) references Categories(CatID))");

        String [] catarr = {
                "Computers",
                "Laptops",
                "Mobiles & Tablets",
                "Cameras",
                "Watches",
                "Televisions",
                "Projectors",
                "Men Clothes",
                "Books",
                "Sports"
        };

        String [] productname_arr = {
                "Alienware_AURORA_Gaming",  // Computers
                "Apple_iMac_2017_MNED2BA",
                "Apple_iMac_MMQA2",
                "Dell_Desktop_9020",
                "Hp_24_XA0001NE_Pavilion",
                "OptiPlex_7070_Tower_Dell_Case",



                "Acer_Predator_Helios_300",  // Labtops
                "Apple_MacBook_Air_2020",
                "Asus_GX701GXR",
                "Dell_G5_155500",
                "HP_Pavilion_15",
                "Lenovo_V15_ADA",



                "Apple_IPhone_11_Pro_Max",  // Tablets and Mobile
                "Apple_IPhone_12_Pro_Max",
                "Huawei_Y6S",
                "Infinix_Note_5",
                "Realme_C12",
                "Xiomi_Redmi_7A",


                "CANON_EOS_250D",  // Cameras
                "Canon_EOS_2000D",
                "Canon_EOS_M50",
                "Nikon_COOLPIX_B500",
                "Nikon_FX_format",
                "Sony_Alpha_a7",



                "Alba_Active_AS9J21X",  // Watches
                "Emporio_Armani",
                "Fossil_Grand_Fs4735",
                "Fossil_Grant_321",
                "Hugo_Boss_Black",
                "Michael_Kors_Dylan_Watch",




                "ARION_LED_32_Inch",  // Televisions
                "GTV_32_Inch",
                "Hoho_32_Inch_HD",
                "Sary_32_Inch",
                "Syinix_32_Inch",
                "Tornado_Led_32_Inch",


                "BenQ_DLP",  // Projectors
                "Canon_Multimedia_LV_X320",
                "Cyber_Uc46hd",
                "EPSON_EBX05",
                "Hd_model_10_UB",
                "HOME_THEATER_YG_600",


                "Blue_Gens",  // Men Clothes
                "ELKHOLY_Fashion",
                "Genedi_Ribbed_Trims",
                "Grinta_Canvas",
                "Hero_Grey",
                "Roadwalker_Round",


                "Assembly_Learning",  // Books
                "C++_And_Object_Oriented",
                "Clean_Code",
                "Introduction_To_Java",
                "Learning_Python",
                "Oracle_Database_Learning",



                "adidas_Weft_knitted_Regista",  // Sports
                "Adidas_Manchester_United",
                "Arsenal_FC",
                "Barcelona_FC",
                "Pro_Hanson_Soccer",
                "Wilson_1858XB_American_Football"

        };


        for(int i = 0 ; i < 10 ; i++){
            ContentValues content = new ContentValues();
            content.put("Catname" , catarr[i]);
            db.insert("Categories" , null , content);
        }

        int catid = 1;
        for(int i = 0 ; i < 60 ; i ++){
            String product_name = productname_arr[i];
            product_name = product_name.replace('_' , ' ');


            int quantity , price;
            quantity = getRandomNumber(2 , 30);
            price    = getRandomNumber(1500 , 50000);

            int j = i + 1;
            if(j % 6 == 1 && j > 1){
                catid++;
            }
            ContentValues content = new ContentValues();
            content.put("ProName" , product_name);
            content.put("Price" , price);
            content.put("Quantity" , quantity);
            content.put("CatID" , catid);
            db.insert("Products" , null , content);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Customers");
        db.execSQL("drop table if exists Categories");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists Products");
        db.execSQL("drop table if exists OrderDetails");
        db.execSQL("drop table if exists pictures");
        onCreate(db);
    }


    // User Function
    public void SignUp(Customer c)
    {
        EcommerceDB = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("Custname" , c.Name);
        content.put("Username" , c.Username);
        content.put("Password" , c.Password);
        content.put("Gender" , c.Gender);
        content.put("Birthdate" , c.BirthDate);
        content.put("Job" , c.Job);
        content.put("Question" , c.Question);
        content.put("Answer" , c.Answer);

        long ret = EcommerceDB.insert("Customers" , null , content);
        EcommerceDB.close();
    }

    public boolean Login(String inusername , String inpassword){
        EcommerceDB = getReadableDatabase();
        String[] mycols = new String[] {"Username" , "Password" , "Question" , "Answer"};
        String[] myargs = new String[] {inusername , inpassword};
        Cursor c = EcommerceDB.query("Customers" , mycols , "Username = ? and Password = ?" ,  myargs , null , null , null);


        if(c.getCount() > 0)
            return true;
        return false;
    }


    public Cursor ForgetPassword(String inusername){
        EcommerceDB = getReadableDatabase();
        String[] mycols = new String[] {"Username" , "Password" , "Question" , "Answer"};
        String[] myargs = new String[] {inusername};
        Cursor c = EcommerceDB.query("Customers" , mycols , "Username = ?" ,  myargs , null , null , null);
        if(c != null)
            c.moveToFirst();
        return c;
    }

    public int GetCustomerrId(String username){
        EcommerceDB = getReadableDatabase();
        Cursor c = EcommerceDB.rawQuery("select CustID from Customers where Username = ?" , new String[]{username});
        c.moveToFirst();
        return Integer.parseInt(c.getString(0));
    }
    //----------------------------------------------------------------------------------


    //Categories
    //-----------------------------------------------------------------------------------
    public Cursor GetAllCategories(){
        EcommerceDB = getReadableDatabase();
        String[] mycols = new String[] {"CatID" , "Catname"};
        String[] myargs = new String[] {};
        Cursor c = EcommerceDB.query("Categories" , mycols , "" ,  myargs , null , null , null);
        if(c != null)
            c.moveToFirst();
        return c;

    }

    public int GetCategoryID(String category_name){
        EcommerceDB = getReadableDatabase();
        Cursor c = EcommerceDB.rawQuery("select CatID from Categories where Catname = ?" , new String[] {category_name});
        c.moveToFirst();
        return Integer.parseInt(c.getString(0).toString());
    }
    //-----------------------------------------------------------------------------------



    //Product
    //-----------------------------------------------------------------------------------
    public Cursor GetProducts_ForSpecificCategory(int category_id){
        EcommerceDB = getReadableDatabase();
        String str_id = String.valueOf(category_id);
        Cursor c = EcommerceDB.rawQuery("select ProID,ProName,Price,Quantity from Products where CatID = ?" , new String[] {str_id});
        if(c != null)
            c.moveToFirst();
        return c;
    }
    // add it to cart
    public void ReducingQuantitybtOne(int productid , int new_quantity){
        EcommerceDB = getWritableDatabase();
        String q = "update Products set Quantity=" + new_quantity + " where ProID=" + productid;
        EcommerceDB.execSQL(q);
    }

    public int GetQuantityforProduct(int productid){
        EcommerceDB = getReadableDatabase();
        String str_id = String.valueOf(productid);
        Cursor c = EcommerceDB.rawQuery("select Quantity from Products where ProID = ?" , new String[] {str_id});
        if(c != null)
            c.moveToFirst();
        return Integer.parseInt(c.getString(0));
    }
    public void AddMoreQuantityToProduct(int productid , int quantity){
        int oldquantity = GetQuantityforProduct(productid);
        int new_quantity = quantity + oldquantity;
        EcommerceDB = getWritableDatabase();
        String q = "update Products set Quantity=" + new_quantity + " where ProID=" + productid;
        EcommerceDB.execSQL(q);
    }

    public int GetProductPrice(int productid){
        EcommerceDB = getReadableDatabase();
        String str_id = String.valueOf(productid);
        Cursor c = EcommerceDB.rawQuery("select Price from Products where ProID = ?" , new String[] {str_id});
        if(c != null)
            c.moveToFirst();
        return Integer.parseInt(c.getString(0));
    }

    public boolean CheckIFQuantityAvailableForProduct(int productid , int requred_quantity){
        EcommerceDB = getReadableDatabase();
        String str_productid = String.valueOf(productid);
        String str_requred_quantity = String.valueOf(requred_quantity);

        Cursor c = EcommerceDB.rawQuery("select * from Products where ProID = ? and Quantity >= ?" , new String[] {str_productid , str_requred_quantity});
        if(c.getCount() > 0)
            return true;
        return false;
    }
    //-----------------------------------------------------------------------------------



    //Orders
    //-----------------------------------------------------------------------------------
    public int GetMaxOrderID(){
        EcommerceDB = getReadableDatabase();
        Cursor c = EcommerceDB.rawQuery("select max(OrdID) from Orders" , new String[] {});
        int answer = 0;
        try {
            if(c.getCount() > 0) {
                c.moveToFirst();
                answer = Integer.parseInt(c.getString(0).toString());
            }
        }
        catch (Exception e){
            answer = 0;
        }
        return answer;
    }

    public void InsertIntoOrder(String date , int custid , String address){
        EcommerceDB = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("OrdDate" , date);
        content.put("CustID" , custid);
        content.put("Address" , address);

        long ret = EcommerceDB.insert("Orders" , null , content);
        EcommerceDB.close();
    }
    //-----------------------------------------------------------------------------------



    //OrderDetails
    //----------------------------------------------------------------------------------
    public void InsertIntoOrderDetails(int orderid , int prodid , int quantity){
        EcommerceDB = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("OrdID" , orderid);
        content.put("ProID" , prodid);
        content.put("Quantity" , quantity);

        long ret = EcommerceDB.insert("OrderDetails" , null , content);
        EcommerceDB.close();
    }
    //----------------------------------------------------------------------------------




    //Statistics
    public Cursor PieChartFunction(){
        EcommerceDB = getReadableDatabase();
        Cursor c = EcommerceDB.rawQuery("select ProID,Quantity from OrderDetails" , new String[] {});
        c.moveToFirst();
        EcommerceDB.close();
        return c;
    }

    public String RetriveCategoryNameByProductID(int productid){
        EcommerceDB = getReadableDatabase();
        Cursor c = EcommerceDB.rawQuery("select Catname from Categories,Products where Products.CatID=Categories.CatID and ProID=?" , new String[] {String.valueOf(productid)});
        c.moveToFirst();
        EcommerceDB.close();
        return c.getString(0);
    }


    public String BarChartFunction(int productid){
        EcommerceDB = getReadableDatabase();
        Cursor c = EcommerceDB.rawQuery("select ProName from Products where ProID=?" , new String[] {String.valueOf(productid)});
        c.moveToFirst();
        EcommerceDB.close();
        return c.getString(0);
    }
}
