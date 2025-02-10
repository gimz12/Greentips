import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.greentipskotlin.App.Model.Admin
import com.example.greentipskotlin.App.Model.Buyer
import com.example.greentipskotlin.App.Model.BuyerOrder
import com.example.greentipskotlin.App.Model.BuyerPayment
import com.example.greentipskotlin.App.Model.Cart
import com.example.greentipskotlin.App.Model.Catalogue
import com.example.greentipskotlin.App.Model.Ceo
import com.example.greentipskotlin.App.Model.Coconut
import com.example.greentipskotlin.App.Model.CoconutProductionReport
import com.example.greentipskotlin.App.Model.CreditCard
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.App.Model.EmployeePosition
import com.example.greentipskotlin.App.Model.Estate
import com.example.greentipskotlin.App.Model.ExpenseDetails
import com.example.greentipskotlin.App.Model.Fertilizer
import com.example.greentipskotlin.App.Model.FieldManager
import com.example.greentipskotlin.App.Model.HarvestInfo
import com.example.greentipskotlin.App.Model.IntercropProductionReport
import com.example.greentipskotlin.App.Model.Intercrops
import com.example.greentipskotlin.App.Model.Invoice
import com.example.greentipskotlin.App.Model.OrderItem
import com.example.greentipskotlin.App.Model.OrderItemReport
import com.example.greentipskotlin.App.Model.Receipt
import com.example.greentipskotlin.App.Model.Resources
import com.example.greentipskotlin.App.Model.Supplier
import com.example.greentipskotlin.App.Model.SupplierOrder
import com.example.greentipskotlin.App.Model.SupplierPayment
import com.example.greentipskotlin.App.Model.Task
import com.example.greentipskotlin.App.Model.Worker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GreentipsDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Greentips.db"
        private const val DATABASE_VERSION = 1

        // Employee Position Table
        private const val TABLE_EMPLOYEE_POSITION = "EmployeePosition"
        private const val EMPLOYEE_POSITION_ID = "id"
        private const val COLUMN_POSITION_NAME = "positionName"
        private const val COLUMN_POSITION_DESCRIPTION = "positionDescription"
        private const val COLUMN_POSITION_STATE = "positionState"

        //Employee Table
        const val TABLE_EMPLOYEE = "Employee"
        const val Employee_ID = "id"
        const val COLUMN_EMPLOYEE_NAME = "employeeName"
        const val COLUMN_PHONE = "phoneNumber"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_JOIN_DATE = "joinDate"
        const val COLUMN_DOB = "dateOfBirth"
        const val COLUMN_AGE = "age"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_EMPLOYEE_POSITION_ID_FR = "employeePositionId"
        const val COLUMN_PROFILE_IMAGE = "profileImage"

        //Admin Table
        const val TABLE_ADMIN = "Admin"
        const val Admin_Employee_ID = "employee_id"
        const val Admin_Shift_Start_Time  = "shift_start_time"
        const val Admin_Shift_End_Time  = "shift_end_time"

        //CEO Table
        const val TABLE_CEO = "Ceo"
        const val CEO_Employee_ID = "employee_id"
        const val CEO_Skills  = "skills"
        const val CEO_Experience  = "experience"

        //FieldManager Table
        const val TABLE_FIELD_MANAGER = "FieldManager"
        const val FIELD_MANAGER_Employee_ID = "employee_id"
        const val FIELD_MANAGER_ESTATE_ID_FR  = "estate_id"
        const val FIELD_MANAGER_EXPERIENCE   = "experience"
        const val FIELD_MANAGER_QUALIFICATION    = "qualification"


        //Worker Table
        const val TABLE_WORKER = "Worker"
        const val WORKER_Employee_ID = "employee_id"
        const val WORKER_ESTATE_ID_FR  = "estate_id"
        const val WORKER_Shift_Start_Time  = "shift_start_time"
        const val WORKER_Shift_End_Time   = "shift_end_time"
        const val WORKER_EXPERIENCE   = "qualification"

        //Task Assignment Table
        const val TABLE_TASK_ASSIGNMENT = "Task_Assignment"
        const val TASK_ASSIGNMENT_ID = "task_assignment_id"
        const val TASK_ASSIGNMENT_TASK_ID  = "task_id"
        const val TASK_ASSIGNMENT_WORKER_ID  = "employee_id"

        //Invoice Table
        const val TABLE_INVOICE = "Invoice"
        const val INVOICE_ID = "invoice_id"
        const val INVOICE_ORDER_ID = "buyer_order_id"
        const val INVOICE_ORDER_PAYMENT_ID  = "buyer_payment"
        const val INVOICE_DATE  = "date"
        const val INVOICE_TIME  = "time"
        const val INVOICE_SUBTOTAL  = "subtotal"
        const val INVOICE_TOTAL  = "total"

        //Receipt Table
        const val TABLE_RECEIPT = "Receipt"
        const val RECEIPT_ID = "invoice_id"
        const val RECEIPT_INVOICE_ID = "invoice_id"
        const val RECEIPT_DATE  = "date"
        const val RECEIPT_TIME  = "time"


        //Task Table
        const val TABLE_TASK = "Task"
        const val TASK_ID = "task_id"
        const val TASK_ESTATE_ID_FR  = "estate_id"
        const val TASK_NAME  = "task_name"
        const val TASK_DESCRIPTION  = "task_description"
        const val TASK_TYPE   = "task_type"
        const val TASK_ASSIGN_DATE   = "task_assign_date"
        const val TASK_PROGRESS   = "task_progress"
        const val TASK_DUE_DATE   = "task_due_date"
        const val TASK_CHALLENGES   = "task_challenges"
        const val TASK_SOLUTION   = "task_solution"

        //Catalogue Table
        const val TABLE_CATALOGUE = "Catalogue"
        const val CATALOGUE_ID = "catalogue_id"
        const val CATALOGUE_NAME  = "catalogue_name"
        const val CATALOGUE_ITEM_TYPE  = "catalogue_item_type"
        const val CATALOGUE_ITEM_PRICE   = "catalogue_item_price"
        const val CATALOGUE_ITEM_QUANTITY   = "catalogue_item_quantity"
        const val CATALOGUE_ITEM_DESCRIPTION   = "catalogue_item_description"
        const val CATALOGUE_ITEM_AVAILABILITY   = "catalogue_item_availability"
        const val CATALOGUE_ITEM_IMAGE = "catalogue_item_image"

        //Cart table
        const val TABLE_CART = "Cart"
        const val CART_ID = "cart_id"
        const val CART_USER_ID  = "cart_user_id"
        const val CART_ITEM_NAME  = "cart_item_name"
        const val CART_ITEM_PRICE  = "cart_item_price"
        const val CART_ITEM_DATE  = "cart_item_date"
        const val CART_ITEM_QUANTITY  = "cart_item_quantity"
        const val CART_ITEM_TOTAL_PRICE  = "cart_item_total_price"

        //Buyer Order Table
        const val TABLE_BUYER_ORDER = "Buyer_Order"
        const val BUYER_ORDER_ID = "order_id"
        const val BUYER_USER_ID = "user_id"
        const val BUYER_ORDER_COST = "order_cost"
        const val BUYER_ORDER_DATE = "order_date"
        const val BUYER_ORDER_STATUS = "order_status"
        const val BUYER_ORDER_SHIPPING_ADDRESS = "shipping_address"

        //Order Item Table
        const val TABLE_ORDER_ITEM = "Order_Item"
        const val ORDER_ITEM_ID = "order_item_id"
        const val ORDER_ITEM_ORDER_ID = "order_id"
        const val ORDER_ITEM_NAME = "order_item_name"
        const val ORDER_ITEM_QUANTITY ="order_item_quantity"
        const val ORDER_ITEM_PRICE ="order_item_price"
        const val ORDER_ITEM_TOTAL_PRICE ="order_item_total_price"

        //Supplier Order Table
        const val TABLE_SUPPLIER_ORDER ="Supplier_Order"
        const val SUPPLIER_ORDER_ID = "order_id"
        const val SUPPLIER_USER_ID = "user_id"
        const val SUPPLIER_ITEM_NAME = "item_name"
        const val SUPPLIER_ITEM_QUANTITY = "item_quantity"
        const val SUPPLIER_ITEM_DESCRIPTION = "item_description"
        const val SUPPLIER_ESTIMATE_DELIVERY_DATE = "estimate_delivery_date"
        const val SUPPLIER_QUANTITY_PRICE = "quantity_price"
        const val SUPPLIER_TOTAL_AMOUNT = "total_amount"
        const val SUPPLIER_FIELDMANAGER_STATUS = "field_manager_status"
        const val SUPPLIER_CEO_STATUS = "ceo_status"

        //Supplier Payment Table
        const val TABLE_SUPPLIER_PAYMENT ="Supplier_Payment"
        const val SUPPLIER_PAYMENT_ID = "payment_id"
        const val SUPPLIER_PAYMENT_ORDER_ID = "order_id"
        const val SUPPLIER_PAYMENT_USER_ID = "supplier_id"
        const val SUPPLIER_PAYMENT_DATE = "payment_date"
        const val SUPPLIER_PAYMENT_TIME = "payment_time"
        const val SUPPLIER_PAYMENT_TYPE = "payment_type"
        const val SUPPLIER_PAYMENT_STATUS = "payment_status"
        const val SUPPLIER_REMAIN_AMOUNT = "remain_amount"
        const val SUPPLIER_PAID_AMOUNT = "paid_amount"
        const val SUPPLIER_PAYMENT_TOTAL_AMOUNT = "total_amount"


        //Buyer Payment Table
        const val TABLE_BUYER_PAYMENT = "Buyer_Payment"
        const val PAYMENT_ID = "payment_id"
        const val PAYMENT_ORDER_ID = "order_id"
        const val PAYMENT_USER_ID = "user_id"
        const val PAYMENT_AMOUNT ="payment_amount"
        const val PAYMENT_STATUS ="payment_status"
        const val PAYMENT_METHOD ="payment_method"
        const val PAYMENT_DATE_TIME ="payment_date_time"

        //CreditCard
        const val TABLE_CREDIT_CARDS = "CreditCards"
        const val COLUMN_CARD_ID = "CardId"
        const val COLUMN_USER_ID = "UserId"
        const val COLUMN_CARD_NUMBER = "CardNumber"
        const val COLUMN_EXPIRY_DATE = "ExpiryDate"
        const val COLUMN_CARD_HOLDER_NAME = "CardHolderName"

        //Estate table
        const val TABLE_ESTATE = "Estate"
        const val Estate_ID = "id"
        const val COLUMN_ESTATE_NAME = "estateName"
        const val COLUMN_ESTATE_LOCATION = "estateLocation"
        const val COLUMN_ESTATE_SIZE = "estateSize"
        const val COLUMN_ESTATE_ADDITIONAL_INFO = "estateAdditionalInfo"

        //Coconut Table
        const val TABLE_COCONUT = "Coconut"
        const val Coconut_ID = "id"
        const val COLUMN_COCONUT_PLANT_DATE = "plantDate"
        const val COLUMN_NUMBER_OF_COCONUTS = "numberOfCoconuts"
        const val COLUMN_COCONUT_TREE_AGE= "coconutTreeAge"
        const val COLUMN_TREE_HEALTH= "treeHealth"
        const val COLUMN_COCONUT_TREE_TYPE= "coconutTreeType"
        const val COLUMN_COCONUT_ADDITIONAL_INFO= "additionalInfo"
        const val COLUMN_COCONUT_ESTATE_ID_FR = "employeePositionId"

        //Buyer Table

        const val TABLE_BUYER = "Buyer"
        const val BUYER_ID = "id"
        const val COLUMN_BUYER_NAME = "buyerName"
        const val COLUMN_BUYER_PHONE_NUMBER="buyerPhoneNumber"
        const val COLUMN_BUYER_COMPANY_NAME="companyName"
        const val COLUMN_BUYER_COMPANY_PHONE_NUMBER="companyPhoneNumber"
        const val COLUMN_BUYER_COMPANY_ADDRESS="companyAddress"
        const val COLUMN_BUYER_TYPE="buyerType"
        const val COLUMN_BUYER_EMAIL="buyerEmail"
        const val COLUMN_BUYER_USERNAME="buyerUsername"
        const val COLUMN_BUYER_PASSWORD="buyerPassword"

        //Intercrop Table
        const val TABLE_INTERCROP = "Intercrop"
        const val INTERCROPS_ID ="id"
        const val COLUMN_INTERCROP_TYPE= "intercropType"
        const val COLUMN_INTERCROP_PLANTED_DATE="plantedDate"
        const val COLUMN_INTERCROP_ADDITIONAL_INFO="addtionalInfo"
        const val COLUMN_INTERCROPS_ESTATE_ID_FR="estateId"

        //Harvest Table
        const val TABLE_HARVEST_INFO = "HarvestInfo"
        const val COLUMN_HARVEST_INFO_ID ="id"
        const val COLUMN_HARVEST_INFO_TYPE= "harvestType"
        const val COLUMN_HARVEST_INFO_HARVESTED_DATE="harvestedDate"
        const val COLUMN_HARVEST_INFO_QUANTITY="harvestedQuantity"
        const val COLUMN_HARVEST_INFO_ADDITIONAL_INFO="addtionalInfo"
        const val COLUMN_HARVEST_INFO_ESTATE_ID_FR="estateId"

        //Supplier
        const val TABLE_SUPPLIER = "Supplier"
        const val SUPPLIER_ID = "id"
        const val COLUMN_SUPPLIER_NAME = "supplierName"
        const val COLUMN_SUPPLIER_PHONE_NUMBER="supplierPhoneNumber"
        const val COLUMN_SUPPLIER_COMPANY_NAME="companyName"
        const val COLUMN_SUPPLIER_COMPANY_PHONE_NUMBER="companyPhoneNumber"
        const val COLUMN_SUPPLIER_COMPANY_ADDRESS="companyAddress"
        const val COLUMN_SUPPLIER_TYPE="supplierType"
        const val COLUMN_SUPPLIER_EMAIL="supplierEmail"
        const val COLUMN_SUPPLIER_USERNAME="supplierUsername"
        const val COLUMN_SUPPLIER_PASSWORD="supplierPassword"

        //Fertilizer
        const val TABLE_FERTILIZER = "Fertilizer"
        const val FERTILIZER_ID = "id"
        const val COLUMN_FERTILIZER_NAME = "fertilizerName"
        const val COLUMN_FERTILIZER_TYPE = "fertilizerType"
        const val COLUMN_FERTILIZER_DATE = "fertilizerDate"
        const val COLUMN_FERTILIZER_QUANTITY = "fertilizerQuantity"
        const val COLUMN_FERTILIZER_COMPOSITION = "fertilizerComposition"
        const val COLUMN_FERTILIZER_ADDITIONAL_INFO = "fertilizerAdditionalInfo"
        const val COLUMN_FERTILIZE_ESTATE_ID_FR = "estateId"

        //Resources
        const val TABLE_RESOURCES = "Resources"
        const val RESOURCE_ID = "id"
        const val COLUMN_RESOURCES_DESCRIPTION = "resourcesDescription"
        const val COLUMN_RESOURCES_DATE = "resourcesDate"
        const val COLUMN_RESOURCES_BILL_NUMBER = "resourceBillNumber"
        const val COLUMN_RESOURCES_AMOUNT = "resourceAmount"
        const val COLUMN_RESOURCES_ESTATE_ID_FR = "estateId"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create the EmployeePosition table
        val createEmployeePositionTable = """
            
            CREATE TABLE $TABLE_EMPLOYEE_POSITION (
                $EMPLOYEE_POSITION_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_POSITION_NAME TEXT,
                $COLUMN_POSITION_DESCRIPTION TEXT,
                $COLUMN_POSITION_STATE TEXT
            )
        """

        val createEmployeeTable = """
            
            CREATE TABLE $TABLE_EMPLOYEE (
                $Employee_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_EMPLOYEE_NAME TEXT,
                $COLUMN_PHONE TEXT,
                $COLUMN_ADDRESS TEXT,
                $COLUMN_GENDER TEXT,
                $COLUMN_JOIN_DATE DATE,
                $COLUMN_DOB DATE,
                $COLUMN_AGE INTEGER,
                $COLUMN_USERNAME TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_PASSWORD TEXT,
                $COLUMN_EMPLOYEE_POSITION_ID_FR INTEGER,
                $COLUMN_PROFILE_IMAGE TEXT,
        FOREIGN KEY($COLUMN_EMPLOYEE_POSITION_ID_FR) REFERENCES $TABLE_EMPLOYEE_POSITION($EMPLOYEE_POSITION_ID)
            )
        """

        val createAdminTable="""
           
            CREATE TABLE $TABLE_ADMIN (
                $Admin_Employee_ID INTEGER PRIMARY KEY ,
                $Admin_Shift_Start_Time TEXT,
                $Admin_Shift_End_Time TEXT)
            
        """

        val createCEOTable="""
           
            CREATE TABLE $TABLE_CEO (
                $CEO_Employee_ID INTEGER PRIMARY KEY ,
                $CEO_Skills TEXT,
                $CEO_Experience TEXT)
            
        """

        val createFieldManagerTable="""
           
            CREATE TABLE $TABLE_FIELD_MANAGER (
                $FIELD_MANAGER_Employee_ID INTEGER PRIMARY KEY,
                $FIELD_MANAGER_ESTATE_ID_FR INTEGER,
                $FIELD_MANAGER_EXPERIENCE TEXT,
                $FIELD_MANAGER_QUALIFICATION TEXT,
                FOREIGN KEY($FIELD_MANAGER_ESTATE_ID_FR) REFERENCES $TABLE_ESTATE($Estate_ID)
);

        """

        val createWorkerTable="""
           
            CREATE TABLE $TABLE_WORKER (
                $WORKER_Employee_ID INTEGER PRIMARY KEY ,
                $WORKER_ESTATE_ID_FR INTEGER,
                $WORKER_Shift_Start_Time TEXT,
                $WORKER_Shift_End_Time TEXT,
                $WORKER_EXPERIENCE TEXT,
                FOREIGN KEY($WORKER_ESTATE_ID_FR) REFERENCES $TABLE_ESTATE($Estate_ID))
            
        """

        val createCatalogueTable= """
            
            CREATE TABLE $TABLE_CATALOGUE (
                $CATALOGUE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $CATALOGUE_NAME TEXT,
                $CATALOGUE_ITEM_TYPE TEXT,
                $CATALOGUE_ITEM_PRICE Double,
                $CATALOGUE_ITEM_QUANTITY INTEGER,
                $CATALOGUE_ITEM_DESCRIPTION TEXT,
                $CATALOGUE_ITEM_AVAILABILITY TEXT,
                $CATALOGUE_ITEM_IMAGE TEXT
                )
            
        """

        val createBuyerOrderTable= """
            
            CREATE TABLE $TABLE_BUYER_ORDER (
                $BUYER_ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $BUYER_USER_ID INTEGER NOT NULL,
                $BUYER_ORDER_COST Double NOT NULL,
                $BUYER_ORDER_DATE TEXT NOT NULL,
                $BUYER_ORDER_STATUS TEXT NOT NULL,
                $BUYER_ORDER_SHIPPING_ADDRESS TEXT NOT NULL,
                FOREIGN KEY($BUYER_USER_ID) REFERENCES $TABLE_BUYER($BUYER_ID))

            
        """

        val createBuyerOrderItems= """
            
            CREATE TABLE $TABLE_ORDER_ITEM (
                $ORDER_ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $ORDER_ITEM_ORDER_ID INTEGER NOT NULL,
                $ORDER_ITEM_NAME TEXT NOT NULL,
                $ORDER_ITEM_QUANTITY INTEGER NOT NULL,
                $ORDER_ITEM_PRICE Double NOT NULL,
                $ORDER_ITEM_TOTAL_PRICE Double NOT NULL,
                FOREIGN KEY($ORDER_ITEM_ORDER_ID) REFERENCES $TABLE_BUYER_ORDER($BUYER_ORDER_ID))

            
        """

        val createCartTable= """
            
            CREATE TABLE $TABLE_CART (
                $CART_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $CART_USER_ID INTEGER NOT NULL,
                $CART_ITEM_NAME TEXT NOT NULL,
                $CART_ITEM_PRICE Double NOT NULL,
                $CART_ITEM_QUANTITY INTEGER NOT NULL,
                $CART_ITEM_DATE TEXT NOT NULL,
                $CART_ITEM_TOTAL_PRICE Double NOT NULL,
                FOREIGN KEY($CART_USER_ID) REFERENCES $TABLE_BUYER($BUYER_ID))

            
        """

        val createBuyerPayment= """
            
            CREATE TABLE $TABLE_BUYER_PAYMENT (
                $PAYMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $PAYMENT_ORDER_ID INTEGER NOT NULL,
                $PAYMENT_USER_ID INTEGER NOT NULL,
                $PAYMENT_AMOUNT Double NOT NULL,
                $PAYMENT_STATUS TEXT NOT NULL,
                $PAYMENT_METHOD TEXT NOT NULL,
                $PAYMENT_DATE_TIME Double NOT NULL,
                FOREIGN KEY($PAYMENT_ORDER_ID) REFERENCES $TABLE_BUYER_ORDER($BUYER_ORDER_ID),
                FOREIGN KEY($PAYMENT_USER_ID) REFERENCES $TABLE_BUYER($BUYER_ID))

            
        """

        val createSupplierOrder= """
            
            CREATE TABLE $TABLE_SUPPLIER_ORDER (
                $SUPPLIER_ORDER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $SUPPLIER_USER_ID INTEGER NOT NULL,
                $SUPPLIER_ITEM_NAME TEXT NOT NULL,
                $SUPPLIER_ITEM_QUANTITY INTEGER NOT NULL,
                $SUPPLIER_ITEM_DESCRIPTION TEXT NOT NULL,
                $SUPPLIER_ESTIMATE_DELIVERY_DATE TEXT NOT NULL,
                $SUPPLIER_QUANTITY_PRICE Double NOT NULL,
                $SUPPLIER_TOTAL_AMOUNT Double NOT NULL,
                $SUPPLIER_FIELDMANAGER_STATUS TEXT NOT NULL,
                $SUPPLIER_CEO_STATUS TEXT NOT NULL,
                FOREIGN KEY($SUPPLIER_USER_ID) REFERENCES $TABLE_SUPPLIER($SUPPLIER_ID))

            
        """

        val createSupplierPayment= """
            
            CREATE TABLE $TABLE_SUPPLIER_PAYMENT (
                $SUPPLIER_PAYMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $SUPPLIER_PAYMENT_ORDER_ID INTEGER NOT NULL,
                $SUPPLIER_PAYMENT_USER_ID INTEGER NOT NULL,
                $SUPPLIER_PAYMENT_DATE TEXT NOT NULL,
                $SUPPLIER_PAYMENT_TIME TEXT NOT NULL,
                $SUPPLIER_PAYMENT_TYPE TEXT NOT NULL,
                $SUPPLIER_PAYMENT_STATUS TEXT NOT NULL,
                $SUPPLIER_REMAIN_AMOUNT Double NOT NULL,
                $SUPPLIER_PAID_AMOUNT Double NOT NULL,
                $SUPPLIER_PAYMENT_TOTAL_AMOUNT Double NOT NULL,
                FOREIGN KEY($SUPPLIER_PAYMENT_ORDER_ID) REFERENCES $TABLE_SUPPLIER_ORDER($SUPPLIER_ORDER_ID),
                FOREIGN KEY($SUPPLIER_PAYMENT_USER_ID) REFERENCES $TABLE_SUPPLIER($SUPPLIER_ID))

            
        """

        val createCreditCardsTable= """
            
                CREATE TABLE $TABLE_CREDIT_CARDS (
                $COLUMN_CARD_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_ID INTEGER NOT NULL,
                $COLUMN_CARD_NUMBER TEXT NOT NULL,
                $COLUMN_EXPIRY_DATE TEXT NOT NULL,
                $COLUMN_CARD_HOLDER_NAME TEXT NOT NULL,
                FOREIGN KEY ($COLUMN_USER_ID) REFERENCES $TABLE_BUYER($BUYER_ID)
    )
"""
        

        val createEstateTable= """
            
            CREATE TABLE $TABLE_ESTATE (
                $Estate_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ESTATE_NAME TEXT,
                $COLUMN_ESTATE_LOCATION TEXT,
                $COLUMN_ESTATE_SIZE TEXT,
                $COLUMN_ESTATE_ADDITIONAL_INFO TEXT)
            
        """

        val createTaskTable= """
            
            CREATE TABLE $TABLE_TASK (
                $TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $TASK_ESTATE_ID_FR INTEGER NOT NULL,
                $TASK_NAME TEXT NOT NULL,
                $TASK_DESCRIPTION TEXT NOT NULL,
                $TASK_TYPE TEXT NOT NULL,
                $TASK_ASSIGN_DATE TEXT NOT NULL,
                $TASK_PROGRESS TEXT NOT NULL,
                $TASK_DUE_DATE TEXT NOT NULL,
                $TASK_CHALLENGES TEXT ,
                $TASK_SOLUTION TEXT,
                FOREIGN KEY($TASK_ESTATE_ID_FR) REFERENCES $TABLE_ESTATE($Estate_ID)
                )
            
        """

        val createTaskAssignmentTable = """
            
            CREATE TABLE $TABLE_TASK_ASSIGNMENT (
                $TASK_ASSIGNMENT_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $TASK_ID INTEGER NOT NULL,
                $WORKER_Employee_ID INTEGER NOT NULL,
                FOREIGN KEY($TASK_ID) REFERENCES $TABLE_TASK($TASK_ID),
                FOREIGN KEY($WORKER_Employee_ID) REFERENCES $TABLE_WORKER($WORKER_Employee_ID)
    )
"""

        val createInvoiceTable = """
            CREATE TABLE $TABLE_INVOICE (
                $INVOICE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $INVOICE_ORDER_ID INTEGER ,
                $INVOICE_ORDER_PAYMENT_ID INTEGER,
                $INVOICE_DATE TEXT,
                $INVOICE_TIME TEXT,
                $INVOICE_SUBTOTAL REAL,
                $INVOICE_TOTAL REAL,
                FOREIGN KEY($INVOICE_ORDER_ID) REFERENCES $TABLE_BUYER_ORDER($BUYER_ORDER_ID),
                FOREIGN KEY($INVOICE_ORDER_PAYMENT_ID) REFERENCES $TABLE_BUYER_PAYMENT($PAYMENT_ID)
            )
        """

        val createReceiptTable = """
            CREATE TABLE $TABLE_RECEIPT (
                $RECEIPT_INVOICE_ID TEXT PRIMARY KEY,
                $RECEIPT_DATE TEXT,
                $RECEIPT_TIME TEXT,
                FOREIGN KEY ($RECEIPT_INVOICE_ID) REFERENCES $TABLE_INVOICE($INVOICE_ORDER_ID)
            )
        """


        val createCoconutTable="""
            CREATE TABLE $TABLE_COCONUT(
            $Coconut_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_COCONUT_PLANT_DATE TEXT,
            $COLUMN_NUMBER_OF_COCONUTS TEXT,
            $COLUMN_COCONUT_TREE_AGE TEXT,
            $COLUMN_TREE_HEALTH TEXT,
            $COLUMN_COCONUT_TREE_TYPE TEXT,
            $COLUMN_COCONUT_ADDITIONAL_INFO TEXT,
            $COLUMN_COCONUT_ESTATE_ID_FR INTEGER,
        FOREIGN KEY($COLUMN_COCONUT_ESTATE_ID_FR) REFERENCES $TABLE_ESTATE($Estate_ID)
            )
            
        """

        val createBuyerTable=""" CREATE TABLE $TABLE_BUYER(
            $BUYER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_BUYER_NAME TEXT,
            $COLUMN_BUYER_PHONE_NUMBER TEXT,
            $COLUMN_BUYER_COMPANY_NAME TEXT,
            $COLUMN_BUYER_COMPANY_PHONE_NUMBER TEXT,
            $COLUMN_BUYER_COMPANY_ADDRESS TEXT,
            $COLUMN_BUYER_TYPE TEXT,
            $COLUMN_BUYER_EMAIL TEXT,
            $COLUMN_BUYER_USERNAME TEXT,
            $COLUMN_BUYER_PASSWORD TEXT)"""

        val createIntercropTable=""" CREATE TABLE $TABLE_INTERCROP(
            $INTERCROPS_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_INTERCROP_TYPE TEXT,
            $COLUMN_INTERCROP_PLANTED_DATE TEXT,
            $COLUMN_INTERCROP_ADDITIONAL_INFO TEXT,
            $COLUMN_INTERCROPS_ESTATE_ID_FR INTEGER,
            FOREIGN KEY ($COLUMN_INTERCROPS_ESTATE_ID_FR) REFERENCES $TABLE_ESTATE($Estate_ID)
        )"""

        val createHarvestTable=""" CREATE TABLE $TABLE_HARVEST_INFO(
            $COLUMN_HARVEST_INFO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_HARVEST_INFO_TYPE TEXT,
            $COLUMN_HARVEST_INFO_HARVESTED_DATE TEXT,
            $COLUMN_HARVEST_INFO_QUANTITY INTEGER,
            $COLUMN_HARVEST_INFO_ADDITIONAL_INFO TEXT,
            $COLUMN_HARVEST_INFO_ESTATE_ID_FR INTEGER,
            FOREIGN KEY ($COLUMN_HARVEST_INFO_ESTATE_ID_FR) REFERENCES $TABLE_ESTATE($Estate_ID)
        )"""

        val createSupplierTable=""" CREATE TABLE $TABLE_SUPPLIER(
            $SUPPLIER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_SUPPLIER_NAME TEXT,
            $COLUMN_SUPPLIER_PHONE_NUMBER TEXT,
            $COLUMN_SUPPLIER_COMPANY_NAME TEXT,
            $COLUMN_SUPPLIER_COMPANY_PHONE_NUMBER TEXT,
            $COLUMN_SUPPLIER_COMPANY_ADDRESS TEXT,
            $COLUMN_SUPPLIER_TYPE TEXT,
            $COLUMN_SUPPLIER_EMAIL TEXT,
            $COLUMN_SUPPLIER_USERNAME TEXT,
            $COLUMN_SUPPLIER_PASSWORD TEXT)"""

        val createFertilizerTable="""CREATE TABLE $TABLE_FERTILIZER(
            $FERTILIZER_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
            $COLUMN_FERTILIZER_NAME TEXT,
            $COLUMN_FERTILIZER_TYPE TEXT, 
            $COLUMN_FERTILIZER_DATE TEXT,
            $COLUMN_FERTILIZER_QUANTITY INTEGER,
            $COLUMN_FERTILIZER_COMPOSITION TEXT,
            $COLUMN_FERTILIZER_ADDITIONAL_INFO TEXT,
            $COLUMN_FERTILIZE_ESTATE_ID_FR INTEGER,
            FOREIGN KEY ($COLUMN_FERTILIZE_ESTATE_ID_FR) REFERENCES $TABLE_ESTATE($Estate_ID))"""


        val createResourcesTable = """ CREATE TABLE $TABLE_RESOURCES(
        $RESOURCE_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_RESOURCES_DESCRIPTION TEXT,
        $COLUMN_RESOURCES_DATE TEXT,
        $COLUMN_RESOURCES_BILL_NUMBER TEXT ,
        $COLUMN_RESOURCES_AMOUNT REAL,
        $COLUMN_RESOURCES_ESTATE_ID_FR INTEGER,
        FOREIGN KEY ($COLUMN_RESOURCES_ESTATE_ID_FR) REFERENCES $TABLE_ESTATE($Estate_ID)
        )
            
        """

        db.execSQL(createEstateTable)
        db.execSQL(createEmployeePositionTable)
        db.execSQL(createEmployeeTable)
        db.execSQL(createCoconutTable)
        db.execSQL(createBuyerTable)
        db.execSQL(createIntercropTable)
        db.execSQL(createHarvestTable)
        db.execSQL(createSupplierTable)
        db.execSQL(createFertilizerTable)
        db.execSQL(createResourcesTable)
        db.execSQL(createAdminTable)
        db.execSQL(createCEOTable)
        db.execSQL(createFieldManagerTable)
        db.execSQL(createWorkerTable)
        db.execSQL(createCatalogueTable)
        db.execSQL(createCartTable)
        db.execSQL(createBuyerOrderTable)
        db.execSQL(createBuyerOrderItems)
        db.execSQL(createBuyerPayment)
        db.execSQL(createCreditCardsTable)
        db.execSQL(createSupplierOrder)
        db.execSQL(createSupplierPayment)
        db.execSQL(createTaskTable)
        db.execSQL(createTaskAssignmentTable)
        db.execSQL(createInvoiceTable)
        db.execSQL(createReceiptTable)



    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLOYEE_POSITION")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLOYEE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ESTATE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COCONUT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BUYER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INTERCROP")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HARVEST_INFO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SUPPLIER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FERTILIZER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RESOURCES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ADMIN")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CEO")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FIELD_MANAGER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_WORKER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATALOGUE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CART")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BUYER_ORDER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BUYER_PAYMENT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDER_ITEM")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CREDIT_CARDS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SUPPLIER_ORDER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SUPPLIER_PAYMENT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TASK_ASSIGNMENT")
        onCreate(db)
    }

    //Employee------------------------------------------------------------------------------------------------


    // Method to insert a new position
    fun insertEmployeePosition(employeePosition: EmployeePosition){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_POSITION_NAME, employeePosition.positionName)
            put(COLUMN_POSITION_DESCRIPTION, employeePosition.positionDescription)
            put(COLUMN_POSITION_STATE, employeePosition.positionState)
        }

        // Insert the row and return the result
        db.insert(TABLE_EMPLOYEE_POSITION, null, values)
        db.close()
    }

    //Method for insert a new employee
    fun insertEmployee(employee: Employee){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMPLOYEE_NAME,employee.employeeName)
            put(COLUMN_PHONE,employee.phoneNumber)
            put(COLUMN_ADDRESS,employee.address)
            put(COLUMN_GENDER,employee.gender)
            put(COLUMN_JOIN_DATE,employee.joinDate.toString())
            put(COLUMN_DOB,employee.dateOfBirth.toString())
            put(COLUMN_AGE,employee.age)
            put(COLUMN_USERNAME,employee.username)
            put(COLUMN_EMAIL,employee.email)
            put(COLUMN_PASSWORD,employee.password)
            put(COLUMN_EMPLOYEE_POSITION_ID_FR,employee.employeePositionId)
            put(COLUMN_PROFILE_IMAGE, employee.profileImage)
        }
        db.insert(TABLE_EMPLOYEE, null, values)
        db.close()
    }

    //to get all employees
    fun getAllEmployees(): List<Employee> {
        val employees = ArrayList<Employee>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_EMPLOYEE", null)


        if (cursor.moveToFirst()) {
            do {
                val employeeId=cursor.getInt(cursor.getColumnIndexOrThrow(Employee_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMPLOYEE_NAME))
                val phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
                val address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS))
                val gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER))

                // Parsing Date fields
                val joinDateString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOIN_DATE))
                val dateOfBirthString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB))
                val joinDate = Date(joinDateString) // Or use a proper date format parser if needed
                val dateOfBirth = Date(dateOfBirthString)

                val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
                val username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
                val positionId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EMPLOYEE_POSITION_ID_FR))
                val profilePicture = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_PROFILE_IMAGE))

                // Add employee to the list
                employees.add(Employee(employeeId,name, phoneNumber, address, gender, joinDate, dateOfBirth, age, username, email, password, positionId,profilePicture))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return employees
    }

    fun validateUser(username: String, password: String): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery(
            "SELECT employeePositionId FROM $TABLE_EMPLOYEE WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(username, password)
        )
    }

    fun authenticateUser(username: String, password: String): Pair<String, Int>? {
        val db = readableDatabase

        // Query Buyer Table
        val buyerCursor = db.rawQuery(
            "SELECT $BUYER_ID FROM $TABLE_BUYER WHERE $COLUMN_BUYER_USERNAME = ? AND $COLUMN_BUYER_PASSWORD = ?",
            arrayOf(username, password)
        )
        if (buyerCursor.moveToFirst()) {
            val buyerId = buyerCursor.getInt(0)
            buyerCursor.close()
            return Pair("Buyer", buyerId)
        }
        buyerCursor.close()

        // Query Supplier Table
        val supplierCursor = db.rawQuery(
            "SELECT $SUPPLIER_ID FROM $TABLE_SUPPLIER WHERE $COLUMN_SUPPLIER_USERNAME = ? AND $COLUMN_SUPPLIER_PASSWORD = ?",
            arrayOf(username, password)
        )
        if (supplierCursor.moveToFirst()) {
            val supplierId = supplierCursor.getInt(0)
            supplierCursor.close()
            return Pair("Supplier", supplierId)
        }
        supplierCursor.close()

        return null // User not found
    }

    fun getSupplierDetailsById(supplierId: Int): Supplier? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $SUPPLIER_ID, $COLUMN_SUPPLIER_NAME, $COLUMN_SUPPLIER_PHONE_NUMBER, $COLUMN_SUPPLIER_COMPANY_NAME, $COLUMN_SUPPLIER_COMPANY_PHONE_NUMBER, $COLUMN_SUPPLIER_COMPANY_ADDRESS, $COLUMN_SUPPLIER_TYPE, $COLUMN_SUPPLIER_EMAIL, $COLUMN_SUPPLIER_USERNAME, $COLUMN_SUPPLIER_PASSWORD FROM $TABLE_SUPPLIER WHERE $SUPPLIER_ID = ?",
            arrayOf(supplierId.toString())
        )
        return if (cursor.moveToFirst()) {
            val supplierId = cursor.getInt(0)
            val supplierName = cursor.getString(1)
            val supplierPhoneNumber = cursor.getString(2)
            val supplierCompanyName = cursor.getString(3)
            val supplierCompanyNumber = cursor.getString(4)
            val supplierCompanyAddress = cursor.getString(5)
            val supplierType = cursor.getString(6)
            val supplierEmail = cursor.getString(7)
            val supplierUsername = cursor.getString(8)
            val supplierPassword = cursor.getString(9)

            cursor.close()
            Supplier(
                supplierId,
                supplierName,
                supplierPhoneNumber,
                supplierCompanyName,
                supplierCompanyNumber,
                supplierCompanyAddress,
                supplierType,
                supplierEmail,
                supplierUsername,
                supplierPassword
            )
        } else {
            cursor.close()
            null
        }
    }


    fun getBuyerDetailsById(buyerId: Int): Buyer? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $BUYER_ID, $COLUMN_BUYER_NAME, $COLUMN_BUYER_PHONE_NUMBER, $COLUMN_BUYER_COMPANY_NAME, $COLUMN_BUYER_COMPANY_PHONE_NUMBER, $COLUMN_BUYER_COMPANY_ADDRESS, $COLUMN_BUYER_TYPE, $COLUMN_BUYER_EMAIL, $COLUMN_BUYER_USERNAME, $COLUMN_BUYER_PASSWORD FROM $TABLE_BUYER WHERE $BUYER_ID = ?",
            arrayOf(buyerId.toString())
        )
        return if (cursor.moveToFirst()) {
            val buyerId = cursor.getInt(0)
            val buyerName = cursor.getString(1)
            val buyerPhoneNumber = cursor.getString(2)
            val buyerCompanyName = cursor.getString(3)
            val buyerCompanyPhoneNumber = cursor.getString(4)
            val buyerCompanyAddress = cursor.getString(5)
            val buyerType = cursor.getString(6)
            val buyerEmail = cursor.getString(7)
            val buyerUsername = cursor.getString(8)
            val buyerPassword = cursor.getString(9)

            cursor.close()
            Buyer(
                Buyer_Id = buyerId,
                Buyer_Name = buyerName,
                Buyer_PhoneNumber = buyerPhoneNumber,
                Buyer_Company_Name = buyerCompanyName,
                Buyer_Company_Number = buyerCompanyPhoneNumber,
                Buyer_Company_Address = buyerCompanyAddress,
                Buyer_Type = buyerType,
                Buyer_Email = buyerEmail,
                Buyer_Username = buyerUsername,
                Buyer_password = buyerPassword
            )
        } else {
            cursor.close()
            null
        }
    }





    fun getEmployeeIdByUsername(username: String): Int? {
        val db = this.readableDatabase
        val query = "SELECT $Employee_ID FROM $TABLE_EMPLOYEE WHERE $COLUMN_USERNAME = ?"
        val cursor = db.rawQuery(query, arrayOf(username))

        return if (cursor.moveToFirst()) {
            val employeeId = cursor.getInt(cursor.getColumnIndex("$Employee_ID"))
            cursor.close()
            employeeId
        } else {
            cursor.close()
            null
        }
    }

    fun getLoggedInUserDetails(username: String, password: String): Employee? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_EMPLOYEE WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(username, password)
        )

        var employee: Employee? = null

        if (cursor.moveToFirst()) {
            // Extract data for the employee
            val employeeId = cursor.getInt(cursor.getColumnIndexOrThrow(Employee_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMPLOYEE_NAME))
            val phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS))
            val gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER))

            // Parsing Date fields
            val joinDateString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_JOIN_DATE))
            val dateOfBirthString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB))
            val joinDate = Date(joinDateString) // Use a proper date format parser if necessary
            val dateOfBirth = Date(dateOfBirthString)

            val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            val positionId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EMPLOYEE_POSITION_ID_FR))
            val profilePicture = cursor.getString(cursor.getColumnIndexOrThrow(
                COLUMN_PROFILE_IMAGE))

            // Create an Employee object with the data from the cursor
            employee = Employee(employeeId, name, phoneNumber, address, gender, joinDate, dateOfBirth, age, username, email, password, positionId,profilePicture)
        }

        cursor.close()
        db.close()

        return employee // Return the employee details or null if not found
    }

    fun updateEmployee(employee: Employee): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMPLOYEE_NAME, employee.employeeName)
            put(COLUMN_EMPLOYEE_POSITION_ID_FR, employee.employeePositionId)
            put(COLUMN_PHONE, employee.phoneNumber)
            put(COLUMN_EMAIL, employee.email)
            put(COLUMN_AGE, employee.age)
        }
        // Update the employee in the database by matching the employee ID
        val rowsAffected = db.update(
            TABLE_EMPLOYEE,
            values,
            "$Employee_ID=?",
            arrayOf(employee.employeeId.toString())
        )
        db.close() // Ensure database is closed
        return rowsAffected
    }


    fun getEmployeeById(id: Int): Employee? {
        val db = this.readableDatabase
        var employee: Employee? = null

        val query = "SELECT * FROM $TABLE_EMPLOYEE WHERE $Employee_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            employee = Employee(
                employeeId = cursor.getInt(cursor.getColumnIndexOrThrow(Employee_ID)),
                employeeName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMPLOYEE_NAME)),
                phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                employeePositionId = cursor.getInt(cursor.getColumnIndexOrThrow(
                    COLUMN_EMPLOYEE_POSITION_ID_FR)),
                gender = null,
                joinDate = null,
                dateOfBirth = null,
                age = null,
                username = null,
                address = null,
                password = null,
                profileImage = null
            )
        }
        cursor.close()
        db.close()
        return employee
    }



    //Admin--------------------------------------------------------------------------------------------------------------------------------

    fun insertAdminDetails(admin: Admin){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(Admin_Employee_ID, admin.EmployerId)
            put(Admin_Shift_Start_Time, admin.ShiftStartTime)
            put(Admin_Shift_End_Time, admin.ShiftEndTime)
        }

        // Insert the row and return the result
        db.insert(TABLE_ADMIN, null, values)
        db.close()
    }

    fun getAdminById(id: Int): Admin? {
        val db = this.readableDatabase
        var admin: Admin? = null

        val query = "SELECT * FROM $TABLE_ADMIN WHERE $Admin_Employee_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            admin = Admin(
                EmployerId = cursor.getInt(cursor.getColumnIndexOrThrow(Admin_Employee_ID)),
                ShiftStartTime = cursor.getString(cursor.getColumnIndexOrThrow(
                    Admin_Shift_Start_Time)),
                ShiftEndTime = cursor.getString(cursor.getColumnIndexOrThrow(Admin_Shift_End_Time))
            )
        }
        cursor.close()
        db.close()
        return admin
    }

    fun updateAdmin(admin: Admin): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(Admin_Shift_Start_Time, admin.ShiftStartTime)
            put(Admin_Shift_End_Time, admin.ShiftEndTime)
        }
        // Update the employee in the database by matching the employee ID
        val rowsAffected = db.update(
            TABLE_ADMIN,
            values,
            "$Admin_Employee_ID=?",
            arrayOf(admin.EmployerId.toString())
        )
        db.close() // Ensure database is closed
        return rowsAffected
    }


    //CEO--------------------------------------------------------------------------------------------------------------------------------

    fun insertCEODetails(ceo: Ceo){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(CEO_Employee_ID, ceo.EmployerId)
            put(CEO_Skills, ceo.Skills)
            put(CEO_Experience, ceo.Experience)
        }

        // Insert the row and return the result
        db.insert(TABLE_CEO, null, values)
        db.close()
    }

    fun getCEOById(id: Int): Ceo? {
        val db = this.readableDatabase
        var ceo: Ceo? = null

        val query = "SELECT * FROM $TABLE_CEO WHERE $CEO_Employee_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            ceo = Ceo(
                EmployerId = cursor.getInt(cursor.getColumnIndexOrThrow(CEO_Employee_ID)),
                Skills = cursor.getString(cursor.getColumnIndexOrThrow(
                    CEO_Skills)),
                Experience  = cursor.getString(cursor.getColumnIndexOrThrow(CEO_Experience))
            )
        }
        cursor.close()
        db.close()
        return ceo
    }

    fun updateCEO(ceo: Ceo): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(CEO_Skills, ceo.Skills)
            put(CEO_Experience, ceo.Experience)
        }
        // Update the employee in the database by matching the employee ID
        val rowsAffected = db.update(
            TABLE_CEO,
            values,
            "$CEO_Employee_ID=?",
            arrayOf(ceo.EmployerId.toString())
        )
        db.close() // Ensure database is closed
        return rowsAffected
    }

    fun insertTask(task: Task){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(TASK_ESTATE_ID_FR, task.TASK_ESTATE_ID_FR)
            put(TASK_NAME, task.TASK_NAME)
            put(TASK_DESCRIPTION, task.TASK_DESCRIPTION)
            put(TASK_TYPE, task.TASK_TYPE)
            put(TASK_ASSIGN_DATE, task.TASK_ASSIGN_DATE)
            put(TASK_PROGRESS, task.TASK_PROGRESS)
            put(TASK_DUE_DATE, task.TASK_DUE_DATE)
            put(TASK_CHALLENGES, task.TASK_CHALLENGES)
            put(TASK_SOLUTION, task.TASK_SOLUTION)
        }
        db.insert(TABLE_TASK, null, values)
        db.close()
    }

    fun updateTask(taskId: Int, taskName: String, taskDescription: String, taskType: String, taskAssignDate: String, taskDueDate: String, taskEstateId: Int): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(TASK_NAME, taskName)
            put(TASK_DESCRIPTION, taskDescription)
            put(TASK_TYPE, taskType)
            put(TASK_ASSIGN_DATE, taskAssignDate)
            put(TASK_DUE_DATE, taskDueDate)
            put(TASK_ESTATE_ID_FR, taskEstateId)
        }

        // Update task by its TASK_ID
        val result = db.update(TABLE_TASK, values, "$TASK_ID = ?", arrayOf(taskId.toString()))
        db.close()

        return result
    }


    fun updateTaskChallenges(taskId: Int, newChallenges: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(TASK_CHALLENGES, newChallenges)
        }
        val rowsUpdated = db.update(TABLE_TASK, values, "$TASK_ID = ?", arrayOf(taskId.toString()))
        db.close()
        return rowsUpdated
    }

    fun updateTaskProgress(taskId: Int, newProgress: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(TASK_PROGRESS, newProgress)
        }

        val rowsAffected = db.update(TABLE_TASK, values, "$TASK_ID = ?", arrayOf(taskId.toString()))
        db.close()
    }

    fun updateTaskSolution(taskId: Int, taskSolution: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(TASK_SOLUTION, taskSolution)
        }

        // Update only TASK_SOLUTION by TASK_ID
        val result = db.update(TABLE_TASK, values, "$TASK_ID = ?", arrayOf(taskId.toString()))
        db.close()

        return result
    }

    fun deleteTask(taskId: Int): Int {
        val db = this.writableDatabase
        // Delete task by its TASK_ID
        val result = db.delete(TABLE_TASK, "$TASK_ID = ?", arrayOf(taskId.toString()))
        db.close()
        return result // returns the number of rows deleted
    }

    fun deleteTaskAssignmentByTaskId(taskId: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_TASK_ASSIGNMENT, "$TASK_ID=?", arrayOf(taskId.toString()))
        db.close()
        return result > 0
    }


    fun getAllTasks(): List<Task> {
        val taskList = mutableListOf<Task>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_TASK, null, null, null, null, null, null)

        if (cursor != null && cursor.moveToFirst()) {
            do {
                val task = Task(
                    TASK_ID = cursor.getInt(cursor.getColumnIndex(TASK_ID)),
                    TASK_ESTATE_ID_FR = cursor.getInt(cursor.getColumnIndex(TASK_ESTATE_ID_FR)),
                    TASK_NAME = cursor.getString(cursor.getColumnIndex(TASK_NAME)),
                    TASK_DESCRIPTION = cursor.getString(cursor.getColumnIndex(TASK_DESCRIPTION)),
                    TASK_TYPE = cursor.getString(cursor.getColumnIndex(TASK_TYPE)),
                    TASK_ASSIGN_DATE = cursor.getString(cursor.getColumnIndex(TASK_ASSIGN_DATE)),
                    TASK_PROGRESS = cursor.getString(cursor.getColumnIndex(TASK_PROGRESS)),
                    TASK_DUE_DATE = cursor.getString(cursor.getColumnIndex(TASK_DUE_DATE)),
                    TASK_CHALLENGES = cursor.getString(cursor.getColumnIndex(TASK_CHALLENGES)),
                    TASK_SOLUTION = cursor.getString(cursor.getColumnIndex(TASK_SOLUTION))
                )
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        cursor?.close()
        db.close()
        return taskList
    }
    fun getTasksByEstateId(estateId: Int?): List<Task> {
        val taskList = mutableListOf<Task>()
        val db = this.readableDatabase

        // Update the query to filter out tasks with 'Task Completed' progress
        val query = "SELECT * FROM $TABLE_TASK WHERE $TASK_ESTATE_ID_FR = ? AND $TASK_PROGRESS != ?"
        val cursor = db.rawQuery(query, arrayOf(estateId.toString(), "Task Complete"))

        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    TASK_ID = cursor.getInt(cursor.getColumnIndexOrThrow(TASK_ID)),
                    TASK_ESTATE_ID_FR = cursor.getInt(cursor.getColumnIndexOrThrow(TASK_ESTATE_ID_FR)),
                    TASK_NAME = cursor.getString(cursor.getColumnIndexOrThrow(TASK_NAME)),
                    TASK_DESCRIPTION = cursor.getString(cursor.getColumnIndexOrThrow(TASK_DESCRIPTION)),
                    TASK_TYPE = cursor.getString(cursor.getColumnIndexOrThrow(TASK_TYPE)),
                    TASK_ASSIGN_DATE = cursor.getString(cursor.getColumnIndexOrThrow(TASK_ASSIGN_DATE)),
                    TASK_PROGRESS = cursor.getString(cursor.getColumnIndexOrThrow(TASK_PROGRESS)),
                    TASK_DUE_DATE = cursor.getString(cursor.getColumnIndexOrThrow(TASK_DUE_DATE)),
                    TASK_CHALLENGES = cursor.getString(cursor.getColumnIndexOrThrow(TASK_CHALLENGES)),
                    TASK_SOLUTION = cursor.getString(cursor.getColumnIndexOrThrow(TASK_SOLUTION))
                )
                taskList.add(task)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return taskList
    }


    //Invoice & Receipts--------------------------------------------------------------------------------------------------------------------------

    fun insertInvoice(invoice: Invoice): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(INVOICE_ORDER_ID, invoice.orderId)
            put(INVOICE_ORDER_PAYMENT_ID, invoice.paymentId)
            put(INVOICE_DATE, invoice.date)
            put(INVOICE_TIME, invoice.time)
            put(INVOICE_SUBTOTAL, invoice.subtotal)
            put(INVOICE_TOTAL, invoice.total)
        }
        // Insert data into the invoice table
        return db.insert(TABLE_INVOICE, null, values)
    }

    // Insert Receipt
    fun insertReceipt(receipt: Receipt): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(RECEIPT_INVOICE_ID, receipt.invoiceId)
            put(RECEIPT_DATE, receipt.date)
            put(RECEIPT_TIME, receipt.time)
        }
        // Insert data into the receipt table
        return db.insert(TABLE_RECEIPT, null, values)
    }

    // Optional: Retrieve all invoices
    fun getAllInvoices(): List<Invoice> {
        val db = readableDatabase
        val cursor = db.query(TABLE_INVOICE, null, null, null, null, null, null)
        val invoices = mutableListOf<Invoice>()

        with(cursor) {
            while (moveToNext()) {
                val invoiceId = getInt(getColumnIndex(INVOICE_ID))
                val orderId = getInt(getColumnIndex(INVOICE_ORDER_ID))
                val paymentId = getInt(getColumnIndex(INVOICE_ORDER_PAYMENT_ID))
                val date = getString(getColumnIndex(INVOICE_DATE))
                val time = getString(getColumnIndex(INVOICE_TIME))
                val subtotal = getDouble(getColumnIndex(INVOICE_SUBTOTAL))
                val total = getDouble(getColumnIndex(INVOICE_TOTAL))

                invoices.add(Invoice(invoiceId, orderId, paymentId, date, time, subtotal, total))
            }
        }
        cursor.close()
        return invoices
    }

    // Optional: Retrieve all receipts
    fun getAllReceipts(): List<Receipt> {
        val db = readableDatabase
        val cursor = db.query(TABLE_RECEIPT, null, null, null, null, null, null)
        val receipts = mutableListOf<Receipt>()

        with(cursor) {
            while (moveToNext()) {
                val receiptId = getInt(getColumnIndex(RECEIPT_ID))
                val invoiceId = getString(getColumnIndex(RECEIPT_INVOICE_ID))
                val date = getString(getColumnIndex(RECEIPT_DATE))
                val time = getString(getColumnIndex(RECEIPT_TIME))

                receipts.add(Receipt(receiptId, invoiceId, date, time))
            }
        }
        cursor.close()
        return receipts
    }



    //FieldManager--------------------------------------------------------------------------------------------------------------------------------

    fun insertFieldManager(fieldManager: FieldManager){
        val db=writableDatabase
        val values = ContentValues().apply {
            put(FIELD_MANAGER_Employee_ID,fieldManager.EmployerId)
            put(FIELD_MANAGER_ESTATE_ID_FR,fieldManager.Estate_ID)
            put(FIELD_MANAGER_EXPERIENCE,fieldManager.Experience)
            put(FIELD_MANAGER_QUALIFICATION,fieldManager.Qualification)
        }
        db.insert(TABLE_FIELD_MANAGER,null,values)
        db.close()

    }

    fun getFieldManagerById(id: Int): FieldManager? {
        val db = this.readableDatabase
        var fieldManager: FieldManager? = null

        val query = "SELECT * FROM $TABLE_FIELD_MANAGER WHERE $FIELD_MANAGER_Employee_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            fieldManager = FieldManager(
                EmployerId = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_MANAGER_Employee_ID)),
                Estate_ID = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_MANAGER_ESTATE_ID_FR)),
                Experience = cursor.getString(cursor.getColumnIndexOrThrow(
                    FIELD_MANAGER_EXPERIENCE)),
                Qualification  = cursor.getString(cursor.getColumnIndexOrThrow(
                    FIELD_MANAGER_QUALIFICATION))
            )
        }
        cursor.close()
        db.close()
        return fieldManager
    }

    fun updateFieldManager(fieldManager: FieldManager): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(FIELD_MANAGER_ESTATE_ID_FR, fieldManager.Estate_ID)
            put(FIELD_MANAGER_QUALIFICATION, fieldManager.Qualification)
            put(FIELD_MANAGER_EXPERIENCE, fieldManager.Experience)
        }
        // Update the employee in the database by matching the employee ID
        val rowsAffected = db.update(
            TABLE_FIELD_MANAGER,
            values,
            "$FIELD_MANAGER_Employee_ID=?",
            arrayOf(fieldManager.EmployerId.toString())
        )
        db.close() // Ensure database is closed
        return rowsAffected
    }

    fun insertCatalogueItem(catalogue: Catalogue){
        val db=writableDatabase
        val values = ContentValues().apply {
            put(CATALOGUE_NAME,catalogue.Catalogue_Name)
            put(CATALOGUE_ITEM_TYPE,catalogue.Catalogue_Item_Type)
            put(CATALOGUE_ITEM_PRICE,catalogue.Catalogue_Item_Price)
            put(CATALOGUE_ITEM_QUANTITY,catalogue.Catalogue_Item_Quantity)
            put(CATALOGUE_ITEM_DESCRIPTION,catalogue.Catalogue_Item_Description)
            put(CATALOGUE_ITEM_AVAILABILITY,catalogue.Catalogue_Item_Availability)
            put(CATALOGUE_ITEM_IMAGE,catalogue.Catalogue_Item_Image)

        }
        db.insert(TABLE_CATALOGUE,null,values)
        db.close()
    }

    fun getItemByCatalogueId(id: Int): Catalogue? {
        val db = this.readableDatabase
        var catalogue: Catalogue? = null

        val query = "SELECT * FROM $TABLE_CATALOGUE WHERE $CATALOGUE_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            catalogue = Catalogue(
                Catalogue_Id = cursor.getInt(cursor.getColumnIndexOrThrow(CATALOGUE_ID)),
                Catalogue_Name = cursor.getString(cursor.getColumnIndexOrThrow(CATALOGUE_NAME)),
                Catalogue_Item_Type = cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_TYPE)),
                Catalogue_Item_Price = cursor.getDouble(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_PRICE)),
                Catalogue_Item_Quantity  = cursor.getInt(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_QUANTITY)),
                Catalogue_Item_Description = cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_DESCRIPTION)),
                Catalogue_Item_Availability = cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_AVAILABILITY))
            )
        }
        cursor.close()
        db.close()
        return catalogue
    }

    fun updateCatalogueItem(catalogue: Catalogue): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(CATALOGUE_NAME, catalogue.Catalogue_Name)
            put(CATALOGUE_ITEM_TYPE, catalogue.Catalogue_Item_Type)
            put(CATALOGUE_ITEM_PRICE, catalogue.Catalogue_Item_Price)
            put(CATALOGUE_ITEM_QUANTITY, catalogue.Catalogue_Item_Quantity)
            put(CATALOGUE_ITEM_DESCRIPTION, catalogue.Catalogue_Item_Description)
            put(CATALOGUE_ITEM_AVAILABILITY, catalogue.Catalogue_Item_Availability)
        }
        // Update the Catalogue Item in the database by matching the Catalogue ID
        val rowsAffected = db.update(
            TABLE_CATALOGUE,
            values,
            "$CATALOGUE_ID=?",
            arrayOf(catalogue.Catalogue_Id.toString())
        )
        db.close() // Ensure database is closed
        return rowsAffected
    }

    fun updateOrderStatus(orderId: Int, newStatus: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(BUYER_ORDER_STATUS, newStatus)
        }

        val rowsUpdated = db.update(TABLE_BUYER_ORDER, values, "$BUYER_ORDER_ID = ?", arrayOf(orderId.toString()))
        db.close()
        return rowsUpdated > 0
    }

    fun getOrderStatus(orderId: Int): String? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_BUYER_ORDER,
            arrayOf(BUYER_ORDER_STATUS),
            "$BUYER_ORDER_ID = ?",
            arrayOf(orderId.toString()),
            null,
            null,
            null
        )

        var status: String? = null
        if (cursor.moveToFirst()) {
            status = cursor.getString(cursor.getColumnIndexOrThrow(BUYER_ORDER_STATUS))
        }
        cursor.close()
        db.close()
        return status
    }

   fun getEstateIdByEmployeeId(employeeId: Int): Int? {
        val db = this.readableDatabase
        var estateId: Int? = null

        val query = "SELECT $FIELD_MANAGER_ESTATE_ID_FR FROM $TABLE_FIELD_MANAGER WHERE $FIELD_MANAGER_Employee_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(employeeId.toString()))

        if (cursor.moveToFirst()) {
            estateId = cursor.getInt(cursor.getColumnIndexOrThrow(FIELD_MANAGER_ESTATE_ID_FR))
        }

        cursor.close()
        db.close()
        return estateId
    }

    fun assignTaskToWorker(taskId: Int, workerId: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(TASK_ID, taskId)
            put(WORKER_Employee_ID, workerId)
        }
        db.insert(TABLE_TASK_ASSIGNMENT, null, values)
        db.close()
    }

    fun getWorkersByTaskId(taskId: Int): List<Int> {
        val db = readableDatabase
        val workers = mutableListOf<Int>()
        val query = "SELECT $WORKER_Employee_ID FROM $TABLE_TASK_ASSIGNMENT WHERE $TASK_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(taskId.toString()))

        while (cursor.moveToNext()) {
            workers.add(cursor.getInt(0))
        }
        cursor.close()
        db.close()
        return workers
    }

    fun removeWorkerFromTask(taskId: Int, workerId: Int) {
        val db = writableDatabase
        db.delete(TABLE_TASK_ASSIGNMENT, "$TASK_ID = ? AND $WORKER_Employee_ID = ?", arrayOf(taskId.toString(), workerId.toString()))
        db.close()
    }






    //Worker--------------------------------------------------------------------------------------------------------------------------------

    fun insertWorker(worker: Worker){
        val db=writableDatabase
        val values = ContentValues().apply {
            put(WORKER_Employee_ID,worker.EmployerId)
            put(WORKER_ESTATE_ID_FR,worker.Estate_ID)
            put(WORKER_Shift_Start_Time,worker.ShiftStartTime)
            put(WORKER_Shift_End_Time,worker.ShiftEndTime)
            put(WORKER_EXPERIENCE,worker.Experience)
        }
        db.insert(TABLE_WORKER,null,values)
        db.close()

    }

    fun getWorkerById(id: Int): Worker? {
        val db = this.readableDatabase
        var worker: Worker? = null

        val query = "SELECT * FROM $TABLE_WORKER WHERE $WORKER_Employee_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))

        if (cursor.moveToFirst()) {
            worker = Worker(
                EmployerId = cursor.getInt(cursor.getColumnIndexOrThrow(WORKER_Employee_ID)),
                Estate_ID = cursor.getInt(cursor.getColumnIndexOrThrow(WORKER_ESTATE_ID_FR)),
                ShiftStartTime = cursor.getString(cursor.getColumnIndexOrThrow(
                    WORKER_Shift_Start_Time)),
                ShiftEndTime = cursor.getString(cursor.getColumnIndexOrThrow(
                    WORKER_Shift_End_Time)),
                Experience  = cursor.getString(cursor.getColumnIndexOrThrow(
                    WORKER_EXPERIENCE))
            )
        }
        cursor.close()
        db.close()
        return worker
    }

    fun updateWorker(worker: Worker): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(WORKER_ESTATE_ID_FR, worker.Estate_ID)
            put(WORKER_Shift_Start_Time, worker.ShiftStartTime)
            put(WORKER_Shift_End_Time, worker.ShiftEndTime)
            put(WORKER_EXPERIENCE, worker.Experience)
        }
        // Update the employee in the database by matching the employee ID
        val rowsAffected = db.update(
            TABLE_WORKER,
            values,
            "$WORKER_Employee_ID=?",
            arrayOf(worker.EmployerId.toString())
        )
        db.close() // Ensure database is closed
        return rowsAffected
    }

    fun getWorkersByEstateId(estateId: Int): List<Worker> {
        val workersList = mutableListOf<Worker>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_WORKER WHERE $WORKER_ESTATE_ID_FR = ?"
        val cursor = db.rawQuery(query, arrayOf(estateId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val worker = Worker(
                    EmployerId = cursor.getInt(cursor.getColumnIndexOrThrow(WORKER_Employee_ID)),
                    Estate_ID = cursor.getInt(cursor.getColumnIndexOrThrow(WORKER_ESTATE_ID_FR)),
                    ShiftStartTime = cursor.getString(cursor.getColumnIndexOrThrow(WORKER_Shift_Start_Time)),
                    ShiftEndTime = cursor.getString(cursor.getColumnIndexOrThrow(WORKER_Shift_End_Time)),
                    Experience = cursor.getString(cursor.getColumnIndexOrThrow(WORKER_EXPERIENCE))
                )
                workersList.add(worker)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return workersList
    }

    fun getAllWorkersByEstate(estateId: Int): List<Worker> {
        val workersList = mutableListOf<Worker>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_WORKER WHERE $WORKER_ESTATE_ID_FR = ?"  // Add estateId condition here
        val cursor = db.rawQuery(query, arrayOf(estateId.toString()))  // Pass estateId as a parameter to the query

        if (cursor.moveToFirst()) {
            do {
                val worker = Worker(
                    EmployerId = cursor.getInt(cursor.getColumnIndexOrThrow(WORKER_Employee_ID)),
                    Estate_ID = cursor.getInt(cursor.getColumnIndexOrThrow(WORKER_ESTATE_ID_FR)),
                    ShiftStartTime = cursor.getString(cursor.getColumnIndexOrThrow(WORKER_Shift_Start_Time)),
                    ShiftEndTime = cursor.getString(cursor.getColumnIndexOrThrow(WORKER_Shift_End_Time)),
                    Experience = cursor.getString(cursor.getColumnIndexOrThrow(WORKER_EXPERIENCE))
                )
                workersList.add(worker)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return workersList
    }



    fun getEmployeeNamesByEstateId(estateId: Int): List<String> {
        val employeeNames = mutableListOf<String>()
        val db = readableDatabase

        val query = """
        SELECT e.$COLUMN_EMPLOYEE_NAME
        FROM $TABLE_EMPLOYEE e
        INNER JOIN $TABLE_WORKER w ON e.$Employee_ID = w.$WORKER_Employee_ID
        WHERE w.$WORKER_ESTATE_ID_FR = ?
    """

        val cursor = db.rawQuery(query, arrayOf(estateId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMPLOYEE_NAME))
                employeeNames.add(name)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return employeeNames
    }


    fun getEmployeeNameById(employeeId: Int?): String {
        val db = readableDatabase
        val query = "SELECT $COLUMN_EMPLOYEE_NAME FROM $TABLE_EMPLOYEE WHERE $Employee_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(employeeId.toString()))

        val name = if (cursor.moveToFirst()) cursor.getString(0) else "Unknown"
        cursor.close()
        db.close()
        return name
    }

    fun getTasksByEmpId(workerEmpId: Int): List<Task> {
        val taskList = mutableListOf<Task>()
        val db = readableDatabase
        val query = """
        SELECT t.*
        FROM $TABLE_TASK t
        JOIN $TABLE_TASK_ASSIGNMENT ta ON t.$TASK_ID = ta.$TASK_ID
        WHERE ta.$WORKER_Employee_ID = ? 
        AND t.$TASK_PROGRESS != 'Task Complete'
    """
        val cursor = db.rawQuery(query, arrayOf(workerEmpId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val taskId = cursor.getInt(cursor.getColumnIndex(TASK_ID))
                val taskEstateIdFr = cursor.getInt(cursor.getColumnIndex(TASK_ESTATE_ID_FR))
                val taskName = cursor.getString(cursor.getColumnIndex(TASK_NAME))
                val taskDescription = cursor.getString(cursor.getColumnIndex(TASK_DESCRIPTION))
                val taskType = cursor.getString(cursor.getColumnIndex(TASK_TYPE))
                val taskAssignDate = cursor.getString(cursor.getColumnIndex(TASK_ASSIGN_DATE))
                val taskProgress = cursor.getString(cursor.getColumnIndex(TASK_PROGRESS))
                val taskDueDate = cursor.getString(cursor.getColumnIndex(TASK_DUE_DATE))
                val taskChallenges = cursor.getString(cursor.getColumnIndex(TASK_CHALLENGES))
                val taskSolution = cursor.getString(cursor.getColumnIndex(TASK_SOLUTION))

                val task = Task(
                    taskId,
                    taskEstateIdFr,
                    taskName,
                    taskDescription,
                    taskType,
                    taskAssignDate,
                    taskProgress,
                    taskDueDate,
                    taskChallenges,
                    taskSolution
                )
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return taskList
    }


    fun getCompletedTasksByEmpId(workerEmpId: Int): List<Task> {
        val taskList = mutableListOf<Task>()
        val db = readableDatabase
        val query = """
        SELECT t.*
        FROM $TABLE_TASK t
        JOIN $TABLE_TASK_ASSIGNMENT ta ON t.$TASK_ID = ta.$TASK_ID
        WHERE ta.$WORKER_Employee_ID = ? AND t.$TASK_PROGRESS = 'Task Complete'
    """
        val cursor = db.rawQuery(query, arrayOf(workerEmpId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val taskId = cursor.getInt(cursor.getColumnIndex(TASK_ID))
                val taskEstateIdFr = cursor.getInt(cursor.getColumnIndex(TASK_ESTATE_ID_FR))
                val taskName = cursor.getString(cursor.getColumnIndex(TASK_NAME))
                val taskDescription = cursor.getString(cursor.getColumnIndex(TASK_DESCRIPTION))
                val taskType = cursor.getString(cursor.getColumnIndex(TASK_TYPE))
                val taskAssignDate = cursor.getString(cursor.getColumnIndex(TASK_ASSIGN_DATE))
                val taskProgress = cursor.getString(cursor.getColumnIndex(TASK_PROGRESS))
                val taskDueDate = cursor.getString(cursor.getColumnIndex(TASK_DUE_DATE))
                val taskChallenges = cursor.getString(cursor.getColumnIndex(TASK_CHALLENGES))
                val taskSolution = cursor.getString(cursor.getColumnIndex(TASK_SOLUTION))

                val task = Task(
                    taskId,
                    taskEstateIdFr,
                    taskName,
                    taskDescription,
                    taskType,
                    taskAssignDate,
                    taskProgress,
                    taskDueDate,
                    taskChallenges,
                    taskSolution
                )
                taskList.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return taskList
    }






    //Estate--------------------------------------------------------------------------------------------------------------------------------



    fun insertEstate(estate: Estate){
        val db=writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ESTATE_NAME,estate.estateName)
            put(COLUMN_ESTATE_LOCATION,estate.estateLocation)
            put(COLUMN_ESTATE_SIZE,estate.estateSize)
            put(COLUMN_ESTATE_ADDITIONAL_INFO,estate.estateAdditionalInfo)
        }
        db.insert(TABLE_ESTATE,null,values)
        db.close()

    }

    fun getAllEstates():List<Estate>{
        val estates = ArrayList<Estate>()
        val db =this.readableDatabase
        val cursor=db.rawQuery("SELECT * FROM $TABLE_ESTATE", null)

        if(cursor.moveToFirst()){
            do{
                val estateID=cursor.getInt(cursor.getColumnIndexOrThrow(Estate_ID))
                val estateName=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTATE_NAME))
                val estateLocation=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_ESTATE_LOCATION))
                val estateSize=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTATE_SIZE))
                val estateAdditionalInfo=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_ESTATE_ADDITIONAL_INFO))

                estates.add(Estate(estateID,estateName,estateLocation,estateSize,estateAdditionalInfo))
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return estates
    }




    //Coconut----------------------------------------------------------------------------------------

    fun insertCoconuts(coconut: Coconut){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_COCONUT_PLANT_DATE,coconut.plantDate)
            put(COLUMN_NUMBER_OF_COCONUTS,coconut.numberOfCoconuts)
            put(COLUMN_COCONUT_TREE_AGE,coconut.coconutTreeAge)
            put(COLUMN_TREE_HEALTH,coconut.treeHealthStatus)
            put(COLUMN_COCONUT_TREE_TYPE,coconut.coconutType)
            put(COLUMN_COCONUT_ADDITIONAL_INFO,coconut.additionalCoconutInfo)
            put(COLUMN_COCONUT_ESTATE_ID_FR,coconut.estateId)

        }
        db.insert(TABLE_COCONUT,null,values)
        db.close()
    }


    fun getAllCoconuts():List<Coconut>{
        val coconuts = ArrayList<Coconut>()
        val db =this.readableDatabase
        val cursor=db.rawQuery("SELECT * FROM $TABLE_COCONUT", null)

        if(cursor.moveToFirst()){
            do{
                val coconutId=cursor.getInt(cursor.getColumnIndexOrThrow(Coconut_ID))
                val estateID=cursor.getInt(cursor.getColumnIndexOrThrow(
                    COLUMN_COCONUT_ESTATE_ID_FR))
                val coconutTreeType=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_COCONUT_TREE_TYPE))
                val coconutPlantedDate=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_COCONUT_PLANT_DATE))
                val coconutAge=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_COCONUT_TREE_AGE))
                val coconutHealth=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_TREE_HEALTH))
                val coconutNumber=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_NUMBER_OF_COCONUTS))
                val coconutAddtionalInfo=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_COCONUT_ADDITIONAL_INFO))

                coconuts.add(Coconut(coconutId,coconutPlantedDate,coconutNumber,coconutAge,coconutHealth,coconutTreeType,coconutAddtionalInfo,estateID))
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return coconuts
    }

    //Buyer--------------------------------------------------------------------------------------------------------------

    fun insertBuyer(buyer: Buyer){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COLUMN_BUYER_NAME,buyer.Buyer_Name)
            put(COLUMN_BUYER_PHONE_NUMBER,buyer.Buyer_PhoneNumber)
            put(COLUMN_BUYER_COMPANY_NAME,buyer.Buyer_Company_Name)
            put(COLUMN_BUYER_COMPANY_PHONE_NUMBER,buyer.Buyer_Company_Number)
            put(COLUMN_BUYER_COMPANY_ADDRESS,buyer.Buyer_Company_Address)
            put(COLUMN_BUYER_TYPE,buyer.Buyer_Type)
            put(COLUMN_BUYER_EMAIL,buyer.Buyer_Email)
            put(COLUMN_BUYER_USERNAME,buyer.Buyer_Username)
            put(COLUMN_BUYER_PASSWORD,buyer.Buyer_password)
        }
        db.insert(TABLE_BUYER,null,values)
        db.close()
    }

    fun getAllBuyers():List<Buyer>{
        val buyers = ArrayList<Buyer>()
        val db =this.readableDatabase
        val cursor=db.rawQuery("SELECT * FROM $TABLE_BUYER", null)

        if(cursor.moveToFirst()){
            do{
                val buyerID=cursor.getInt(cursor.getColumnIndexOrThrow(BUYER_ID))
                val buyerName=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_BUYER_NAME))
                val buyerPhoneNumber=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_BUYER_PHONE_NUMBER))
                val buyerCompanyName=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_BUYER_COMPANY_NAME))
                val buyerCompanyPhoneNumber=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_BUYER_COMPANY_PHONE_NUMBER))
                val buyerCompanyAddress=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_BUYER_COMPANY_ADDRESS))
                val buyerType=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_BUYER_TYPE))
                val buyerEmail=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_BUYER_EMAIL))
                val buyerUsername=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_BUYER_USERNAME))
                val buyerPassword=cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_BUYER_PASSWORD))


                buyers.add(Buyer(buyerID,buyerName,buyerPhoneNumber,buyerCompanyName,buyerCompanyPhoneNumber,buyerCompanyAddress,buyerType,buyerEmail,buyerUsername,buyerPassword))
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return buyers
    }

    fun getAllBuyerCatalogueItems():List<Catalogue>{
        val catalogues = ArrayList<Catalogue>()
        val db =this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CATALOGUE WHERE $CATALOGUE_ITEM_TYPE = 'Buyer' AND $CATALOGUE_ITEM_AVAILABILITY = 'Available'", null)

        if(cursor.moveToFirst()){
            do{
                val catalogueID=cursor.getInt(cursor.getColumnIndexOrThrow(CATALOGUE_ID))
                val catalogueName=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_NAME))
                val catalogueType=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_TYPE))
                val cataloguePrice=cursor.getDouble(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_PRICE))
                val catalogueQuantity=cursor.getInt(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_QUANTITY))
                val catalogueDescription=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_DESCRIPTION))
                val catalogueAvailability=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_AVAILABILITY))
                val catalogueItemImage=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_IMAGE))



                catalogues.add(Catalogue(catalogueID,catalogueName,catalogueType,cataloguePrice,catalogueQuantity,catalogueDescription,catalogueAvailability,catalogueItemImage))
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return catalogues
    }

    fun getAllSupplierCatalogueItems():List<Catalogue>{
        val catalogues = ArrayList<Catalogue>()
        val db =this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CATALOGUE WHERE $CATALOGUE_ITEM_TYPE = 'Supplier' AND $CATALOGUE_ITEM_AVAILABILITY = 'Need'", null)

        if(cursor.moveToFirst()){
            do{
                val catalogueID=cursor.getInt(cursor.getColumnIndexOrThrow(CATALOGUE_ID))
                val catalogueName=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_NAME))
                val catalogueType=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_TYPE))
                val catalogueDescription=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_DESCRIPTION))
                val catalogueAvailability=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_AVAILABILITY))
                val catalogueItemImage=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_IMAGE))



                catalogues.add(Catalogue(catalogueID,catalogueName,catalogueType,null,null,catalogueDescription,catalogueAvailability,catalogueItemImage))
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return catalogues
    }


    fun getAllCatalogueItems():List<Catalogue>{
        val catalogues = ArrayList<Catalogue>()
        val db =this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CATALOGUE", null)

        if(cursor.moveToFirst()){
            do{
                val catalogueID=cursor.getInt(cursor.getColumnIndexOrThrow(CATALOGUE_ID))
                val catalogueName=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_NAME))
                val catalogueType=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_TYPE))
                val cataloguePrice=cursor.getDouble(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_PRICE))
                val catalogueQuantity=cursor.getInt(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_QUANTITY))
                val catalogueDescription=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_DESCRIPTION))
                val catalogueAvailability=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_AVAILABILITY))
                val catalogueItemImage=cursor.getString(cursor.getColumnIndexOrThrow(
                    CATALOGUE_ITEM_IMAGE))



                catalogues.add(Catalogue(catalogueID,catalogueName,catalogueType,cataloguePrice,catalogueQuantity,catalogueDescription,catalogueAvailability,catalogueItemImage))
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return catalogues
    }

    fun addItemToCart(cart: Cart){
        val db=writableDatabase
        val values = ContentValues().apply {
            put(CART_USER_ID,cart.CART_USER_ID)
            put(CART_ITEM_NAME,cart.CART_ITEM_NAME)
            put(CART_ITEM_PRICE,cart.CART_ITEM_PRICE)
            put(CART_ITEM_QUANTITY,cart.CART_ITEM_QUANTITY)
            put(CART_ITEM_DATE,cart.CART_ITEM_DATE)
            put(CART_ITEM_TOTAL_PRICE,cart.CART_ITEM_TOTAL_PRICE)
        }
        db.insert(TABLE_CART,null,values)
        db.close()
    }

    fun getCartItemsByUserId(userId: Int): List<Cart> {
        val cartList = mutableListOf<Cart>()
        val db = readableDatabase

        val query = "SELECT * FROM $TABLE_CART WHERE $CART_USER_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val cart = Cart(
                    Cart_Id = cursor.getInt(cursor.getColumnIndexOrThrow(CART_ID)),
                    CART_USER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(CART_USER_ID)),
                    CART_ITEM_NAME = cursor.getString(cursor.getColumnIndexOrThrow(CART_ITEM_NAME)),
                    CART_ITEM_PRICE = cursor.getDouble(cursor.getColumnIndexOrThrow(CART_ITEM_PRICE)),
                    CART_ITEM_QUANTITY = cursor.getInt(cursor.getColumnIndexOrThrow(CART_ITEM_QUANTITY)),
                    CART_ITEM_DATE = cursor.getString(cursor.getColumnIndexOrThrow(CART_ITEM_DATE)),
                    CART_ITEM_TOTAL_PRICE = cursor.getDouble(cursor.getColumnIndexOrThrow(CART_ITEM_TOTAL_PRICE))
                )
                cartList.add(cart)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return cartList
    }

    fun deleteCartItem(cartId: Int?, userId: Int): Boolean {
        val db = writableDatabase

        // Define the WHERE clause using Cart_ID and userId
        val whereClause = "$CART_ID = ? AND $CART_USER_ID = ?"
        val whereArgs = arrayOf(cartId.toString(), userId.toString())

        // Execute delete query
        val result = db.delete(TABLE_CART, whereClause, whereArgs)

        db.close()

        // Return true if at least one row was deleted
        return result > 0
    }

    fun updateCatalogueQuantity(itemName: String, quantitySold: Int): Boolean {
        val db = this.writableDatabase

        // Fetch the current quantity from the catalogue
        val cursor = db.query(
            TABLE_CATALOGUE,
            arrayOf(CATALOGUE_ITEM_QUANTITY),
            "$CATALOGUE_NAME = ?",
            arrayOf(itemName),
            null, null, null
        )

        var isUpdated = false
        if (cursor != null && cursor.moveToFirst()) {
            val currentQuantity = cursor.getInt(cursor.getColumnIndex(CATALOGUE_ITEM_QUANTITY))

            // Calculate the new quantity
            val newQuantity = currentQuantity - quantitySold

            // Update the quantity in the Catalogue table
            val values = ContentValues().apply {
                put(CATALOGUE_ITEM_QUANTITY, newQuantity)
            }

            val selection = "$CATALOGUE_NAME = ?"
            val selectionArgs = arrayOf(itemName)

            val rowsUpdated = db.update(
                TABLE_CATALOGUE,
                values,
                selection,
                selectionArgs
            )

            if (rowsUpdated > 0) {
                isUpdated = true
            }
        }

        cursor?.close()
        return isUpdated
    }

    fun updateCatalogueQuantityRemove(itemName: String, quantitySold: Int): Boolean {
        val db = this.writableDatabase

        // Fetch the current quantity from the catalogue
        val cursor = db.query(
            TABLE_CATALOGUE,
            arrayOf(CATALOGUE_ITEM_QUANTITY),
            "$CATALOGUE_NAME = ?",
            arrayOf(itemName),
            null, null, null
        )

        var isUpdated = false
        if (cursor != null && cursor.moveToFirst()) {
            val currentQuantity = cursor.getInt(cursor.getColumnIndex(CATALOGUE_ITEM_QUANTITY))

            // Calculate the new quantity
            val newQuantity = currentQuantity + quantitySold

            // Update the quantity in the Catalogue table
            val values = ContentValues().apply {
                put(CATALOGUE_ITEM_QUANTITY, newQuantity)
            }

            val selection = "$CATALOGUE_NAME = ?"
            val selectionArgs = arrayOf(itemName)

            val rowsUpdated = db.update(
                TABLE_CATALOGUE,
                values,
                selection,
                selectionArgs
            )

            if (rowsUpdated > 0) {
                isUpdated = true
            }
        }

        cursor?.close()
        return isUpdated
    }

    fun insertCreditCard(creditCard: CreditCard): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USER_ID, creditCard.userId)
            put(COLUMN_CARD_NUMBER, creditCard.cardNumber)
            put(COLUMN_EXPIRY_DATE, creditCard.expiryDate)
            put(COLUMN_CARD_HOLDER_NAME, creditCard.cardHolderName)
        }
        return db.insert(TABLE_CREDIT_CARDS, null, values)
    }

    fun getCreditCardsByUserId(userId: Int): List<CreditCard> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_CREDIT_CARDS,
            null,
            "$COLUMN_USER_ID = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )
        val creditCards = mutableListOf<CreditCard>()
        while (cursor.moveToNext()) {
            creditCards.add(
                CreditCard(
                    cardId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CARD_ID)),
                    userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    cardNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CARD_NUMBER)),
                    expiryDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY_DATE)),
                    cardHolderName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CARD_HOLDER_NAME))
                )
            )
        }
        cursor.close()
        return creditCards
    }

    fun placeOrder(buyerOrder: BuyerOrder): Long{
        val db=writableDatabase
        val values = ContentValues().apply {
            put(BUYER_USER_ID,buyerOrder.USER_ID)
            put(BUYER_ORDER_COST,buyerOrder.ORDER_COST)
            put(BUYER_ORDER_DATE,buyerOrder.ORDER_DATE)
            put(BUYER_ORDER_STATUS,buyerOrder.ORDER_STATUS)
            put(BUYER_ORDER_SHIPPING_ADDRESS,buyerOrder.ORDER_SHIPPING_ADDRESS)
        }
        val orderId = db.insert(TABLE_BUYER_ORDER, null, values)
        db.close()
        return orderId
    }

    fun clearCartForUser(userId: Int): Boolean {
        val db = this.writableDatabase
        return try {
            // Delete all cart items for the given user
            val rowsAffected = db.delete(TABLE_CART, "$CART_USER_ID = ?", arrayOf(userId.toString()))
            rowsAffected > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.close()
        }
    }

    fun insertOrderItem(orderItem: OrderItem){
        val db=writableDatabase
        val values = ContentValues().apply {
            put(ORDER_ITEM_ORDER_ID,orderItem.ORDER_ITEM_ORDER_ID)
            put(ORDER_ITEM_NAME,orderItem.ORDER_ITEM_NAME)
            put(ORDER_ITEM_PRICE,orderItem.ORDER_ITEM_PRICE)
            put(ORDER_ITEM_QUANTITY,orderItem.ORDER_ITEM_QUANTITY)
            put(ORDER_ITEM_TOTAL_PRICE,orderItem.ORDER_ITEM_TOTAL_PRICE)
        }
        db.insert(TABLE_ORDER_ITEM,null,values)
        db.close()
    }

    fun getOrderItemsByOrderId(orderId: Int): List<OrderItem> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_ORDER_ITEM,
            null, // Select all columns
            "$ORDER_ITEM_ORDER_ID = ?", // WHERE clause
            arrayOf(orderId.toString()), // Arguments for WHERE clause
            null, // GROUP BY
            null, // HAVING
            null  // ORDER BY
        )

        val orderItems = mutableListOf<OrderItem>()
        while (cursor.moveToNext()) {
            val item = OrderItem(
                ORDER_ITEM_ID = cursor.getInt(cursor.getColumnIndexOrThrow(ORDER_ITEM_ID)),
                ORDER_ITEM_ORDER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(ORDER_ITEM_ORDER_ID)),
                ORDER_ITEM_NAME = cursor.getString(cursor.getColumnIndexOrThrow(ORDER_ITEM_NAME)),
                ORDER_ITEM_QUANTITY = cursor.getInt(cursor.getColumnIndexOrThrow(ORDER_ITEM_QUANTITY)),
                ORDER_ITEM_PRICE = cursor.getDouble(cursor.getColumnIndexOrThrow(ORDER_ITEM_PRICE)),
                ORDER_ITEM_TOTAL_PRICE = cursor.getDouble(cursor.getColumnIndexOrThrow(
                    ORDER_ITEM_TOTAL_PRICE))
            )
            orderItems.add(item)
        }
        cursor.close()
        return orderItems
    }

    fun getBuyerOrdersByUserId(userId: Int): List<BuyerOrder> {
        val orderList = mutableListOf<BuyerOrder>()
        val db = readableDatabase

        val query = "SELECT * FROM $TABLE_BUYER_ORDER WHERE $BUYER_USER_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val buyerOrder = BuyerOrder(
                    ORDER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(BUYER_ORDER_ID)),
                    USER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(BUYER_USER_ID)),
                    ORDER_COST = cursor.getDouble(cursor.getColumnIndexOrThrow(BUYER_ORDER_COST)),
                    ORDER_DATE = cursor.getString(cursor.getColumnIndexOrThrow(BUYER_ORDER_DATE)),
                    ORDER_STATUS = cursor.getString(cursor.getColumnIndexOrThrow(BUYER_ORDER_STATUS)),
                    ORDER_SHIPPING_ADDRESS = cursor.getString(cursor.getColumnIndexOrThrow(
                        BUYER_ORDER_SHIPPING_ADDRESS)),
                )
                orderList.add(buyerOrder)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return orderList
    }



    fun getAllBuyerProcessingOrder(): List<BuyerOrder> {
        val orderList = mutableListOf<BuyerOrder>()
        val db = readableDatabase

        val query = "SELECT * FROM $TABLE_BUYER_ORDER WHERE $BUYER_ORDER_STATUS != 'Delivered'"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val buyerOrder = BuyerOrder(
                    ORDER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(BUYER_ORDER_ID)),
                    USER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(BUYER_USER_ID)),
                    ORDER_COST = cursor.getDouble(cursor.getColumnIndexOrThrow(BUYER_ORDER_COST)),
                    ORDER_DATE = cursor.getString(cursor.getColumnIndexOrThrow(BUYER_ORDER_DATE)),
                    ORDER_STATUS = cursor.getString(cursor.getColumnIndexOrThrow(BUYER_ORDER_STATUS)),
                    ORDER_SHIPPING_ADDRESS = cursor.getString(cursor.getColumnIndexOrThrow(
                        BUYER_ORDER_SHIPPING_ADDRESS)),
                )
                orderList.add(buyerOrder)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return orderList
    }

    fun insertBuyerPayment(buyerPayment: BuyerPayment):Long{
        val db=writableDatabase
        val values = ContentValues().apply {
            put(PAYMENT_ID,buyerPayment.PAYMENT_ID)
            put(PAYMENT_ORDER_ID,buyerPayment.PAYMENT_ORDER_ID)
            put(PAYMENT_USER_ID,buyerPayment.PAYMENT_USER_ID)
            put(PAYMENT_AMOUNT,buyerPayment.PAYMENT_AMOUNT)
            put(PAYMENT_STATUS,buyerPayment.PAYMENT_STATUS)
            put(PAYMENT_METHOD,buyerPayment.PAYMENT_METHOD)
            put(PAYMENT_DATE_TIME,buyerPayment.PAYMENT_DATE_TIME)
        }
        val paymentID = db.insert(TABLE_BUYER_PAYMENT,null,values)
        db.close()

        return paymentID
    }

    fun getBuyerPaymentsByUserId(userId: Int): List<BuyerPayment> {
        val buyerPaymentList = mutableListOf<BuyerPayment>()
        val db = readableDatabase

        val query = "SELECT * FROM $TABLE_BUYER_PAYMENT WHERE $PAYMENT_USER_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))

        if (cursor.moveToFirst()) {
            do {
                val buyerPayment = BuyerPayment(
                    PAYMENT_ID = cursor.getInt(cursor.getColumnIndexOrThrow(PAYMENT_ID)),
                    PAYMENT_ORDER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(PAYMENT_ORDER_ID)),
                    PAYMENT_USER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(PAYMENT_USER_ID)),
                    PAYMENT_AMOUNT = cursor.getDouble(cursor.getColumnIndexOrThrow(PAYMENT_AMOUNT)),
                    PAYMENT_STATUS = cursor.getString(cursor.getColumnIndexOrThrow(PAYMENT_STATUS)),
                    PAYMENT_METHOD = cursor.getString(cursor.getColumnIndexOrThrow(
                        PAYMENT_METHOD)),
                    PAYMENT_DATE_TIME = cursor.getString(cursor.getColumnIndexOrThrow(
                        PAYMENT_DATE_TIME)),
                )
                buyerPaymentList.add(buyerPayment)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return buyerPaymentList
    }








    //Intercrops--------------------------------------------------------------------------------------------------------------

    fun insertInterCrops(intercrops: Intercrops){
        val db=writableDatabase
        val values = ContentValues().apply {
            put(INTERCROPS_ID,intercrops.intercropId)
            put(COLUMN_INTERCROP_TYPE,intercrops.intercropType)
            put(COLUMN_INTERCROP_PLANTED_DATE,intercrops.intercropPlantedDate)
            put(COLUMN_INTERCROP_ADDITIONAL_INFO,intercrops.intercropAddtionalInfo)
            put(COLUMN_INTERCROPS_ESTATE_ID_FR,intercrops.intercropEstateID)
        }
        db.insert(TABLE_INTERCROP,null,values)
        db.close()
    }

    fun getAllIntercrops():List<Intercrops>{
        val intercrops = ArrayList<Intercrops>()
        val db = writableDatabase
        val cursor=db.rawQuery("SELECT * FROM $TABLE_INTERCROP",null)

        if (cursor.moveToFirst()){
            do {
                val intercropId = cursor.getInt(cursor.getColumnIndexOrThrow(INTERCROPS_ID))
                val intercropEstateId = cursor.getInt(cursor.getColumnIndexOrThrow(
                    COLUMN_INTERCROPS_ESTATE_ID_FR))
                val intercropsType = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_INTERCROP_TYPE))
                val intercropPlantedDate = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_INTERCROP_PLANTED_DATE))
                val intercropAdditionalInfo = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_INTERCROP_ADDITIONAL_INFO))
                intercrops.add(Intercrops(intercropId,intercropsType,intercropPlantedDate,intercropAdditionalInfo,intercropEstateId))
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return intercrops
    }

    fun getIntercropsForEstate(estateId: Int): List<Intercrops> {
        val intercrops = mutableListOf<Intercrops>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_INTERCROP,
            null,
            "$COLUMN_INTERCROPS_ESTATE_ID_FR = ?",
            arrayOf(estateId.toString()),
            null, null, null
        )
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(INTERCROPS_ID))
                val intercropType = getString(getColumnIndexOrThrow(COLUMN_INTERCROP_TYPE))
                val plantedDate = getString(getColumnIndexOrThrow(COLUMN_INTERCROP_PLANTED_DATE))
                val additionalInfo = getString(getColumnIndexOrThrow(COLUMN_INTERCROP_ADDITIONAL_INFO))
                intercrops.add(Intercrops(id, intercropType, plantedDate, additionalInfo, estateId))
            }
        }
        cursor.close()
        return intercrops
    }



    //HarvestInfo--------------------------------------------------------------------------------------------------------------

    fun insertHarvestInfo(harvestInfo: HarvestInfo){
        val db=writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_HARVEST_INFO_ID,harvestInfo.harvestID)
            put(COLUMN_HARVEST_INFO_TYPE,harvestInfo.harvestType)
            put(COLUMN_HARVEST_INFO_HARVESTED_DATE,harvestInfo.harvestDate)
            put(COLUMN_HARVEST_INFO_QUANTITY,harvestInfo.harvestQuantity)
            put(COLUMN_HARVEST_INFO_ADDITIONAL_INFO,harvestInfo.harvestAddtional_Info)
            put(COLUMN_HARVEST_INFO_ESTATE_ID_FR,harvestInfo.harvestEstate)
        }
        db.insert(TABLE_HARVEST_INFO,null,values)
        db.close()
    }

    fun getAllHarvestInfo():List<HarvestInfo>{
        val harvestInfos= ArrayList<HarvestInfo>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HARVEST_INFO",null)

        if (cursor.moveToFirst()){
            do {
                val harvestInfoId = cursor.getInt(cursor.getColumnIndexOrThrow(
                    COLUMN_HARVEST_INFO_ID))

                val harvestInfoEstateId = cursor.getInt(cursor.getColumnIndexOrThrow(
                    COLUMN_HARVEST_INFO_ESTATE_ID_FR))

                val harvestInfoCropType = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_HARVEST_INFO_TYPE))

                val harvestInfoDate = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_HARVEST_INFO_HARVESTED_DATE))

                val harvestInfoQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(
                    COLUMN_HARVEST_INFO_QUANTITY))

                val harvestInfoAdditionalInfo = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_HARVEST_INFO_ADDITIONAL_INFO))

                harvestInfos.add(
                    HarvestInfo(harvestInfoId,
                    harvestInfoCropType,
                    harvestInfoDate,
                    harvestInfoQuantity,
                    harvestInfoAdditionalInfo,
                    harvestInfoEstateId)
                )
            }
                while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return harvestInfos
    }

    //Supplier--------------------------------------------------------------------------------------------------------------

    fun insertSupplier(supplier: Supplier){
        val db = writableDatabase
        val values=ContentValues().apply {
            put(SUPPLIER_ID,supplier.Supplier_Id)
            put(COLUMN_SUPPLIER_NAME,supplier.Supplier_Name)
            put(COLUMN_SUPPLIER_PHONE_NUMBER,supplier.Supplier_PhoneNumber)
            put(COLUMN_SUPPLIER_COMPANY_NAME,supplier.Supplier_Company_Name)
            put(COLUMN_SUPPLIER_COMPANY_PHONE_NUMBER,supplier.Supplier_Company_Number)
            put(COLUMN_SUPPLIER_COMPANY_ADDRESS,supplier.Supplier_Company_Address)
            put(COLUMN_SUPPLIER_TYPE,supplier.Supplier_Type)
            put(COLUMN_SUPPLIER_EMAIL,supplier.Supplier_Email)
            put(COLUMN_SUPPLIER_USERNAME,supplier.Supplier_Username)
            put(COLUMN_SUPPLIER_PASSWORD,supplier.Supplier_password)
        }
        db.insert(TABLE_SUPPLIER,null,values)
        db.close()
    }

    fun getAllSupplier():List<Supplier>{
        val suppliers= ArrayList<Supplier>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_SUPPLIER",null)

        if (cursor.moveToFirst()){
            do{
                val supplierId = cursor.getInt(cursor.getColumnIndexOrThrow(SUPPLIER_ID))

                val supplierName = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_SUPPLIER_NAME))

                val supplierPhoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_SUPPLIER_PHONE_NUMBER))

                val supplierCompanyName = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_SUPPLIER_COMPANY_NAME))

                val supplierCompanyPhoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_SUPPLIER_COMPANY_PHONE_NUMBER))

                val supplierComapnyAddress = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_SUPPLIER_COMPANY_ADDRESS))

                val supllierType = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_SUPPLIER_TYPE))

                val supplierEmail = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_SUPPLIER_EMAIL))

                val supplierUsername = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_SUPPLIER_USERNAME))

                val supplierPassword = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_SUPPLIER_PASSWORD))

                suppliers.add(Supplier(supplierId,
                    supplierName,
                    supplierPhoneNumber,
                    supplierCompanyName,
                    supplierCompanyPhoneNumber,
                    supplierComapnyAddress,
                    supllierType,
                    supplierEmail,
                    supplierUsername,
                    supplierPassword)
                )

            }
                while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return suppliers
    }

    //Fertilizer--------------------------------------------------------------------------------------------------------------

    fun insertFertilizer(fertilizer: Fertilizer){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(FERTILIZER_ID,fertilizer.fertilizerId)
            put(COLUMN_FERTILIZER_NAME,fertilizer.fertilizerName)
            put(COLUMN_FERTILIZER_TYPE,fertilizer.fertilizerType)
            put(COLUMN_FERTILIZER_QUANTITY,fertilizer.fertilizerQuantity)
            put(COLUMN_FERTILIZER_DATE,fertilizer.fertilizerDate)
            put(COLUMN_FERTILIZER_COMPOSITION,fertilizer.fertilizerComposition)
            put(COLUMN_FERTILIZER_ADDITIONAL_INFO,fertilizer.fertilizerAdditionalInfo)
            put(COLUMN_FERTILIZE_ESTATE_ID_FR,fertilizer.fertilizerEstateId)
        }
        db.insert(TABLE_FERTILIZER,null,values)
        db.close()
    }

    fun getAllFertilizer():List<Fertilizer>{
        val fertilizers= ArrayList<Fertilizer>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_FERTILIZER",null)

        if (cursor.moveToFirst()){
            do {
                val fertilizerId = cursor.getInt(cursor.getColumnIndexOrThrow(FERTILIZER_ID))

                val fertilizerName = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_FERTILIZER_NAME))
                val fertilizerType = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_FERTILIZER_TYPE))
                val fertilizerDate = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_FERTILIZER_DATE))
                val fertilizerQuantity = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_FERTILIZER_QUANTITY))
                val fertilizerComposition = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_FERTILIZER_COMPOSITION))
                val fertilizerAdditionalInfo = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_FERTILIZER_ADDITIONAL_INFO))
                val fertilizerEstateId = cursor.getInt(cursor.getColumnIndexOrThrow(
                    COLUMN_FERTILIZE_ESTATE_ID_FR))

                fertilizers.add(Fertilizer(fertilizerId,fertilizerName,
                    fertilizerType,
                    fertilizerDate,
                    fertilizerQuantity,
                    fertilizerComposition,
                    fertilizerAdditionalInfo,
                    fertilizerEstateId))

            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return fertilizers
    }

    fun insertSupplierOrder(supplierOrder: SupplierOrder){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(SUPPLIER_ORDER_ID,supplierOrder.ORDER_ID)
            put(SUPPLIER_USER_ID,supplierOrder.USER_ID)
            put(SUPPLIER_ITEM_NAME,supplierOrder.ITEM_NAME)
            put(SUPPLIER_ITEM_QUANTITY,supplierOrder.ITEM_QUANTITY)
            put(SUPPLIER_ITEM_DESCRIPTION,supplierOrder.ITEM_DESCRIPTION)
            put(SUPPLIER_ESTIMATE_DELIVERY_DATE,supplierOrder.ESTIMATE_DELIVERY_DATE)
            put(SUPPLIER_QUANTITY_PRICE,supplierOrder.QUANTITY_PRICE)
            put(SUPPLIER_TOTAL_AMOUNT,supplierOrder.TOTAL_AMOUNT)
            put(SUPPLIER_FIELDMANAGER_STATUS,supplierOrder.FIELDMANAGER_STATUS)
            put(SUPPLIER_CEO_STATUS,supplierOrder.CEO_STATUS)
        }
        db.insert(TABLE_SUPPLIER_ORDER,null,values)
        db.close()
    }

    fun insertSupplierPayment(supplierPayment: SupplierPayment){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(SUPPLIER_PAYMENT_ID,supplierPayment.PAYMENT_ID)
            put(SUPPLIER_PAYMENT_ORDER_ID,supplierPayment.PAYMENT_ORDER_ID)
            put(SUPPLIER_PAYMENT_USER_ID,supplierPayment.PAYMENT_USER_ID)
            put(SUPPLIER_PAYMENT_DATE,supplierPayment.PAYMENT_DATE)
            put(SUPPLIER_PAYMENT_TIME,supplierPayment.PAYMENT_TIME)
            put(SUPPLIER_PAYMENT_TYPE,supplierPayment.PAYMENT_TYPE)
            put(SUPPLIER_PAYMENT_STATUS,supplierPayment.PAYMENT_STATUS)
            put(SUPPLIER_REMAIN_AMOUNT,supplierPayment.REMAIN_AMOUNT)
            put(SUPPLIER_PAID_AMOUNT,supplierPayment.PAID_AMOUNT)
            put(SUPPLIER_PAYMENT_TOTAL_AMOUNT,supplierPayment.TOTAL_AMOUNT)
        }
        db.insert(TABLE_SUPPLIER_PAYMENT,null,values)
        db.close()
    }

    fun getFieldManagerApprovedSupplierOrders(): List<SupplierOrder> {
        val orders = mutableListOf<SupplierOrder>()
        val query = "SELECT * FROM $TABLE_SUPPLIER_ORDER WHERE $SUPPLIER_FIELDMANAGER_STATUS = 'Approved' AND $SUPPLIER_CEO_STATUS = 'Pending'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                orders.add(mapCursorToSupplierOrder(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return orders
    }

    fun getCeoApprovedSupplierOrders(): List<SupplierOrder> {
        val orders = mutableListOf<SupplierOrder>()
        val query = """
        SELECT * FROM $TABLE_SUPPLIER_ORDER 
        WHERE $SUPPLIER_CEO_STATUS = 'Approved' 
        AND $SUPPLIER_ORDER_ID NOT IN (
            SELECT $SUPPLIER_PAYMENT_ORDER_ID FROM $TABLE_SUPPLIER_PAYMENT
        )
    """
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                orders.add(mapCursorToSupplierOrder(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return orders
    }

    fun getApprovedSupplierOrders(): List<SupplierOrder> {
        val orders = mutableListOf<SupplierOrder>()
        val query = """
            SELECT * FROM $TABLE_SUPPLIER_ORDER 
            WHERE $SUPPLIER_FIELDMANAGER_STATUS = 'Approved' 
              AND $SUPPLIER_CEO_STATUS = 'Approved'
        """
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                orders.add(mapCursorToSupplierOrder(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return orders
    }


    fun getUnapprovedSupplierOrders(): List<SupplierOrder> {
        val orders = mutableListOf<SupplierOrder>()
        val query = """
            SELECT * FROM $TABLE_SUPPLIER_ORDER 
            WHERE $SUPPLIER_FIELDMANAGER_STATUS = 'Pending' 
              AND $SUPPLIER_CEO_STATUS = 'Pending'
        """
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                orders.add(mapCursorToSupplierOrder(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return orders
    }

    fun getSupplierOrders(): List<SupplierOrder> {
        val orders = mutableListOf<SupplierOrder>()
        val query = "SELECT * FROM $TABLE_SUPPLIER_ORDER"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                orders.add(mapCursorToSupplierOrder(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return orders
    }

    // Helper function to map Cursor to SupplierOrder
    private fun mapCursorToSupplierOrder(cursor: Cursor): SupplierOrder {
        return SupplierOrder(
            ORDER_ID = cursor.getInt(cursor.getColumnIndex(SUPPLIER_ORDER_ID)),
            USER_ID = cursor.getInt(cursor.getColumnIndex(SUPPLIER_USER_ID)),
            ITEM_NAME = cursor.getString(cursor.getColumnIndex(SUPPLIER_ITEM_NAME)),
            ITEM_QUANTITY = cursor.getInt(cursor.getColumnIndex(SUPPLIER_ITEM_QUANTITY)),
            ITEM_DESCRIPTION = cursor.getString(cursor.getColumnIndex(SUPPLIER_ITEM_DESCRIPTION)),
            ESTIMATE_DELIVERY_DATE = cursor.getString(cursor.getColumnIndex(SUPPLIER_ESTIMATE_DELIVERY_DATE)),
            QUANTITY_PRICE = cursor.getDouble(cursor.getColumnIndex(SUPPLIER_QUANTITY_PRICE)),
            TOTAL_AMOUNT = cursor.getDouble(cursor.getColumnIndex(SUPPLIER_TOTAL_AMOUNT)),
            FIELDMANAGER_STATUS = cursor.getString(cursor.getColumnIndex(SUPPLIER_FIELDMANAGER_STATUS)),
            CEO_STATUS = cursor.getString(cursor.getColumnIndex(SUPPLIER_CEO_STATUS))
        )
    }

    fun updateFieldManagerStatus(orderId: Int?, status: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(SUPPLIER_FIELDMANAGER_STATUS, status)
        }

        val rowsAffected = db.update(
            TABLE_SUPPLIER_ORDER,
            values,
            "$SUPPLIER_ORDER_ID = ?",
            arrayOf(orderId.toString())
        )

        db.close()
        return rowsAffected > 0
    }

    fun updateCeoStatus(orderId: Int?, status: String): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(SUPPLIER_CEO_STATUS, status)
        }

        val rowsUpdated = db.update(
            TABLE_SUPPLIER_ORDER,
            contentValues,
            "$SUPPLIER_ORDER_ID = ?",
            arrayOf(orderId.toString())
        )

        db.close()
        return rowsUpdated > 0 // Return true if at least one row was updated
    }

    fun getSupplierPaymentsByStatus(status: String): List<SupplierPayment> {
        val db = readableDatabase
        val payments = mutableListOf<SupplierPayment>()

        val cursor = db.query(
            TABLE_SUPPLIER_PAYMENT,
            null,
            "$SUPPLIER_PAYMENT_STATUS = ?",
            arrayOf(status),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            do {
                val payment = SupplierPayment(
                    PAYMENT_ID = cursor.getInt(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_ID)),
                    PAYMENT_ORDER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_ORDER_ID)),
                    PAYMENT_USER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_USER_ID)),
                    PAYMENT_DATE = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_DATE)),
                    PAYMENT_TIME = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_TIME)),
                    PAYMENT_TYPE = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_TYPE)),
                    PAYMENT_STATUS = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_STATUS)),
                    REMAIN_AMOUNT = cursor.getDouble(cursor.getColumnIndexOrThrow(SUPPLIER_REMAIN_AMOUNT)),
                    PAID_AMOUNT = cursor.getDouble(cursor.getColumnIndexOrThrow(SUPPLIER_PAID_AMOUNT)),
                    TOTAL_AMOUNT = cursor.getDouble(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_TOTAL_AMOUNT))
                )
                payments.add(payment)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return payments
    }

    fun getPartialPaidSupplierPayments(): List<SupplierPayment> {
        val partialPaidPayments = mutableListOf<SupplierPayment>()
        val query = """
        SELECT * FROM $TABLE_SUPPLIER_PAYMENT 
        WHERE $SUPPLIER_PAYMENT_STATUS = 'Partial Paid'
    """
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val payment = SupplierPayment(
                    PAYMENT_ID = cursor.getInt(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_ID)),
                    PAYMENT_ORDER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_ORDER_ID)),
                    PAYMENT_USER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_USER_ID)),
                    PAYMENT_DATE = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_DATE)),
                    PAYMENT_TIME = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_TIME)),
                    PAYMENT_TYPE = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_TYPE)),
                    PAYMENT_STATUS = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_STATUS)),
                    REMAIN_AMOUNT = cursor.getDouble(cursor.getColumnIndexOrThrow(SUPPLIER_REMAIN_AMOUNT)),
                    PAID_AMOUNT = cursor.getDouble(cursor.getColumnIndexOrThrow(SUPPLIER_PAID_AMOUNT)),
                    TOTAL_AMOUNT = cursor.getDouble(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_TOTAL_AMOUNT))
                )
                partialPaidPayments.add(payment)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return partialPaidPayments
    }

    fun updateSupplierPayment(
        paymentId: Int,
        newPaymentDate: String,
        newPaymentTime: String,
        newPaidAmount: Double,
        newRemainAmount: Double,
        newPaymentStatus: String
    ): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(SUPPLIER_PAYMENT_DATE, newPaymentDate)
            put(SUPPLIER_PAYMENT_TIME, newPaymentTime)
            put(SUPPLIER_PAID_AMOUNT, newPaidAmount)
            put(SUPPLIER_REMAIN_AMOUNT, newRemainAmount)
            put(SUPPLIER_PAYMENT_STATUS, newPaymentStatus)
        }

        val rowsUpdated = db.update(
            TABLE_SUPPLIER_PAYMENT,
            values,
            "$SUPPLIER_PAYMENT_ID = ?",
            arrayOf(paymentId.toString())
        )
        db.close()
        return rowsUpdated
    }

    fun getPaymentStatus(orderId: Int): String? {
        val db = readableDatabase
        val query = "SELECT $SUPPLIER_PAYMENT_STATUS FROM $TABLE_SUPPLIER_PAYMENT WHERE $SUPPLIER_PAYMENT_ORDER_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(orderId.toString()))

        var paymentStatus: String? = null
        if (cursor.moveToFirst()) {
            paymentStatus = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_STATUS))
        }
        cursor.close()
        db.close()
        return paymentStatus
    }

    fun getSupplierOrdersByUserId(userId: Int): List<SupplierOrder> {
        val db = readableDatabase
        val supplierOrders = mutableListOf<SupplierOrder>()

        // Define the query
        val cursor = db.query(
            TABLE_SUPPLIER_ORDER, // Table name
            null, // All columns, you can specify columns if needed
            "$SUPPLIER_USER_ID = ?", // WHERE clause
            arrayOf(userId.toString()), // The value for USER_ID
            null, // GROUP BY clause
            null, // HAVING clause
            null // ORDER BY clause (can be added if needed)
        )

        // Loop through the results and map them to SupplierOrder objects
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val order = SupplierOrder(
                    ORDER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(SUPPLIER_ORDER_ID)),
                    USER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(SUPPLIER_USER_ID)),
                    ITEM_NAME = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_ITEM_NAME)),
                    ITEM_QUANTITY = cursor.getInt(cursor.getColumnIndexOrThrow(SUPPLIER_ITEM_QUANTITY)),
                    ITEM_DESCRIPTION = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_ITEM_DESCRIPTION)),
                    ESTIMATE_DELIVERY_DATE = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_ESTIMATE_DELIVERY_DATE)),
                    QUANTITY_PRICE = cursor.getDouble(cursor.getColumnIndexOrThrow(SUPPLIER_QUANTITY_PRICE)),
                    TOTAL_AMOUNT = cursor.getDouble(cursor.getColumnIndexOrThrow(SUPPLIER_TOTAL_AMOUNT)),
                    FIELDMANAGER_STATUS = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_FIELDMANAGER_STATUS)),
                    CEO_STATUS = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_CEO_STATUS))
                )
                supplierOrders.add(order)
            } while (cursor.moveToNext())
        }

        cursor?.close()
        db.close()

        return supplierOrders
    }

    fun updateSupplierOrder(supplierOrder: SupplierOrder) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(SUPPLIER_ITEM_QUANTITY, supplierOrder.ITEM_QUANTITY)
            put(SUPPLIER_ESTIMATE_DELIVERY_DATE, supplierOrder.ESTIMATE_DELIVERY_DATE)
            put(SUPPLIER_QUANTITY_PRICE, supplierOrder.QUANTITY_PRICE)
        }
        // Update the record where the ORDER_ID matches
        val whereClause = "$SUPPLIER_ORDER_ID = ?"
        val whereArgs = arrayOf(supplierOrder.ORDER_ID.toString())
        db.update(TABLE_SUPPLIER_ORDER, values, whereClause, whereArgs)
        db.close()
    }

    fun deleteSupplierOrder(orderId: Int) {
        val db = writableDatabase
        db.delete(TABLE_SUPPLIER_ORDER, "$SUPPLIER_ORDER_ID = ?", arrayOf(orderId.toString()))
        db.close()
    }

    fun getSupplierPaymentByOrderId(orderId: Int): SupplierPayment? {
        val db = readableDatabase
        var supplierPayment: SupplierPayment? = null

        val query = "SELECT * FROM $TABLE_SUPPLIER_PAYMENT WHERE $SUPPLIER_PAYMENT_ORDER_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(orderId.toString()))

        if (cursor.moveToFirst()) {
            supplierPayment = SupplierPayment(
                PAYMENT_ID = cursor.getInt(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_ID)),
                PAYMENT_ORDER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_ORDER_ID)),
                PAYMENT_USER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_USER_ID)),
                PAYMENT_DATE = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_DATE)),
                PAYMENT_TIME = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_TIME)),
                PAYMENT_TYPE = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_TYPE)),
                PAYMENT_STATUS = cursor.getString(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_STATUS)),
                REMAIN_AMOUNT = cursor.getDouble(cursor.getColumnIndexOrThrow(SUPPLIER_REMAIN_AMOUNT)),
                PAID_AMOUNT = cursor.getDouble(cursor.getColumnIndexOrThrow(SUPPLIER_PAID_AMOUNT)),
                TOTAL_AMOUNT = cursor.getDouble(cursor.getColumnIndexOrThrow(SUPPLIER_PAYMENT_TOTAL_AMOUNT))
            )
        }

        cursor.close()
        db.close()
        return supplierPayment
    }



    //Resources--------------------------------------------------------------------------------------------------------------

    fun insertResource(resources: Resources){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(RESOURCE_ID,resources.resourcesID)
            put(COLUMN_RESOURCES_DESCRIPTION,resources.description)
            put(COLUMN_RESOURCES_DATE,resources.date)
            put(COLUMN_RESOURCES_BILL_NUMBER,resources.billNumber)
            put(COLUMN_RESOURCES_AMOUNT,resources.amount)
            put(COLUMN_RESOURCES_ESTATE_ID_FR,resources.resourcesEstate)

        }
        db.insert(TABLE_RESOURCES,null,values)
        db.close()
    }

    fun getAllResources():List<Resources>{
        val resources= ArrayList<Resources>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_RESOURCES",null)

        if (cursor.moveToFirst()){
            do {
                val resourcesId = cursor.getInt(cursor.getColumnIndexOrThrow(RESOURCE_ID))

                val resourcesDescription = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_RESOURCES_DESCRIPTION))
                val resourcesDate = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_RESOURCES_DATE))
                val resourcesBillNumber = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_RESOURCES_BILL_NUMBER))
                val resourcesAmount = cursor.getDouble(cursor.getColumnIndexOrThrow(
                    COLUMN_RESOURCES_AMOUNT))
                val resourcesEstate = cursor.getInt(cursor.getColumnIndexOrThrow(
                    COLUMN_RESOURCES_ESTATE_ID_FR))

                resources.add(Resources(resourcesId,resourcesDescription,
                    resourcesDate,
                    resourcesBillNumber,
                    resourcesAmount,
                    resourcesEstate))

            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return resources
    }

    fun deleteResource(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_RESOURCES, "$RESOURCE_ID=?", arrayOf(id.toString()))
    }

    fun updateResource(resource: Resources): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RESOURCES_DESCRIPTION, resource.description)
            put(COLUMN_RESOURCES_DATE, resource.date)
            put(COLUMN_RESOURCES_BILL_NUMBER, resource.billNumber)
            put(COLUMN_RESOURCES_AMOUNT, resource.amount)
            put(COLUMN_RESOURCES_ESTATE_ID_FR, resource.resourcesEstate)
        }
        return db.update(TABLE_RESOURCES, values, "$RESOURCE_ID=?", arrayOf(resource.resourcesID.toString()))
    }

    fun getRevenueReport(startDate: String, endDate: String): Double {
        var totalRevenue = 0.0
        val db = readableDatabase

        // Query to calculate total revenue from orders and sold items (including quantity and price)
        val query = """
        SELECT SUM(oi.$ORDER_ITEM_TOTAL_PRICE)
        FROM $TABLE_ORDER_ITEM oi
        JOIN $TABLE_BUYER_ORDER bo ON oi.$ORDER_ITEM_ORDER_ID = bo.$BUYER_ORDER_ID
        WHERE bo.$BUYER_ORDER_STATUS != 'Cancelled'
        AND bo.$BUYER_ORDER_DATE BETWEEN ? AND ?
    """

        // Execute the query with the provided date range as arguments
        val cursor = db.rawQuery(query, arrayOf(startDate, endDate))

        // Move to the first result and retrieve the revenue if present
        if (cursor.moveToFirst()) {
            totalRevenue = cursor.getDouble(0)
        }

        cursor.close() // Close the cursor after use
        return totalRevenue
    }

    fun getOrderDetails(startDate: String, endDate: String): List<OrderDetails> {
        val orderDetailsList = mutableListOf<OrderDetails>()
        val db = readableDatabase

        val query = """
    SELECT bo.$BUYER_ORDER_ID, bo.$BUYER_ORDER_DATE, bo.$BUYER_ORDER_COST, 
           oi.$ORDER_ITEM_NAME, oi.$ORDER_ITEM_QUANTITY, oi.$ORDER_ITEM_TOTAL_PRICE
    FROM $TABLE_ORDER_ITEM oi
    JOIN $TABLE_BUYER_ORDER bo ON oi.$ORDER_ITEM_ORDER_ID = bo.$BUYER_ORDER_ID
    WHERE bo.$BUYER_ORDER_STATUS != 'Cancelled'
    AND bo.$BUYER_ORDER_DATE BETWEEN ? AND ?
    ORDER BY bo.$BUYER_ORDER_DATE ASC
"""

        val cursor = db.rawQuery(query, arrayOf(startDate, endDate))

        val orderMap = mutableMapOf<Int, OrderDetails>()

        if (cursor.moveToFirst()) {
            do {
                val orderId = cursor.getInt(0)
                val orderDate = cursor.getString(1)  // Fetching order date
                val orderCost = cursor.getDouble(2)
                val itemName = cursor.getString(3)
                val quantity = cursor.getInt(4)
                val totalPrice = cursor.getDouble(5)

                // If this order already exists in the map, add the item to the list
                val orderDetails = orderMap[orderId]
                    ?: OrderDetails(orderId, orderDate, orderCost, mutableListOf()).also {
                        orderMap[orderId] = it
                    }

                // Add the item to the order's item list
                val orderItem = OrderItemReport(itemName, quantity, totalPrice)
                orderDetails.items.add(orderItem)

            } while (cursor.moveToNext())
        }

        cursor.close()

        // Return the list of all orders with their items
        return orderMap.values.toList()
    }

    fun getExpensesReport(startDate: String, endDate: String): List<ExpenseDetails> {
        val expensesList = mutableListOf<ExpenseDetails>()
        val db = readableDatabase
        val query = """
        SELECT so.$SUPPLIER_ORDER_ID, s.$COLUMN_SUPPLIER_NAME, s.$COLUMN_SUPPLIER_COMPANY_NAME, 
               so.$SUPPLIER_ITEM_NAME, so.$SUPPLIER_ITEM_QUANTITY, 
               so.$SUPPLIER_QUANTITY_PRICE, so.$SUPPLIER_TOTAL_AMOUNT
        FROM $TABLE_SUPPLIER_ORDER so
        JOIN $TABLE_SUPPLIER_PAYMENT sp ON so.$SUPPLIER_ORDER_ID = sp.$SUPPLIER_PAYMENT_ORDER_ID
        JOIN $TABLE_SUPPLIER s ON so.$SUPPLIER_USER_ID = s.$SUPPLIER_ID
        WHERE sp.$SUPPLIER_PAYMENT_DATE BETWEEN ? AND ?
    """

        val cursor = db.rawQuery(query, arrayOf(startDate, endDate))

        var subTotal = 0.0
        if (cursor.moveToFirst()) {
            do {
                val orderId = cursor.getInt(0)
                val userName = cursor.getString(1)
                val companyName = cursor.getString(2)
                val itemName = cursor.getString(3)
                val itemQuantity = cursor.getInt(4)
                val quantityPrice = cursor.getDouble(5)
                val totalAmount = cursor.getDouble(6)

                subTotal += totalAmount

                expensesList.add(
                    ExpenseDetails(orderId, userName, companyName, itemName, itemQuantity, quantityPrice, totalAmount)
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return expensesList
    }

    fun getBuyerOrderById(orderId: Int): BuyerOrder? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_BUYER_ORDER WHERE $BUYER_ORDER_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(orderId.toString()))

        var buyerOrder: BuyerOrder? = null
        if (cursor.moveToFirst()) {
            buyerOrder = BuyerOrder(
                ORDER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(BUYER_ORDER_ID)),
                USER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(BUYER_USER_ID)),
                ORDER_COST = cursor.getDouble(cursor.getColumnIndexOrThrow(BUYER_ORDER_COST)),
                ORDER_DATE = cursor.getString(cursor.getColumnIndexOrThrow(BUYER_ORDER_DATE)),
                ORDER_STATUS = cursor.getString(cursor.getColumnIndexOrThrow(BUYER_ORDER_STATUS)),
                ORDER_SHIPPING_ADDRESS = cursor.getString(cursor.getColumnIndexOrThrow(BUYER_ORDER_SHIPPING_ADDRESS))
            )
        }
        cursor.close()
        db.close()

        return buyerOrder
    }

    fun getBuyerPaymentByOrderId(orderId: Int): BuyerPayment? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_BUYER_PAYMENT WHERE $PAYMENT_ORDER_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(orderId.toString()))

        var buyerPayment: BuyerPayment? = null
        if (cursor.moveToFirst()) {
            buyerPayment = BuyerPayment(
                PAYMENT_ID = cursor.getInt(cursor.getColumnIndexOrThrow(PAYMENT_ID)),
                PAYMENT_ORDER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(PAYMENT_ORDER_ID)),
                PAYMENT_USER_ID = cursor.getInt(cursor.getColumnIndexOrThrow(PAYMENT_USER_ID)),
                PAYMENT_AMOUNT = cursor.getDouble(cursor.getColumnIndexOrThrow(PAYMENT_AMOUNT)),
                PAYMENT_STATUS = cursor.getString(cursor.getColumnIndexOrThrow(PAYMENT_STATUS)),
                PAYMENT_METHOD = cursor.getString(cursor.getColumnIndexOrThrow(PAYMENT_METHOD)),
                PAYMENT_DATE_TIME = cursor.getString(cursor.getColumnIndexOrThrow(PAYMENT_DATE_TIME))
            )
        }
        cursor.close()
        db.close()

        return buyerPayment
    }

    //Custom Reports -------------------------------------------------------------------------------------------------------

    fun getCoconutProductionReport(): List<CoconutProductionReport> {
        val productionList = mutableListOf<CoconutProductionReport>()
        val db = readableDatabase

        val query = """
        SELECT 
            $COLUMN_COCONUT_ESTATE_ID_FR, 
            $COLUMN_COCONUT_PLANT_DATE,
            SUM(CAST($COLUMN_NUMBER_OF_COCONUTS AS INTEGER)) AS TotalCoconuts,
            $COLUMN_COCONUT_TREE_AGE,
            $COLUMN_TREE_HEALTH,
            $COLUMN_COCONUT_TREE_TYPE,
            $COLUMN_COCONUT_ADDITIONAL_INFO
        FROM $TABLE_COCONUT
        GROUP BY 
            $COLUMN_COCONUT_ESTATE_ID_FR, 
            $COLUMN_COCONUT_PLANT_DATE,
            $COLUMN_COCONUT_TREE_AGE, 
            $COLUMN_TREE_HEALTH,
            $COLUMN_COCONUT_TREE_TYPE, 
            $COLUMN_COCONUT_ADDITIONAL_INFO
        ORDER BY 
            $COLUMN_COCONUT_ESTATE_ID_FR, 
            $COLUMN_COCONUT_PLANT_DATE,
            $COLUMN_COCONUT_TREE_AGE;
    """

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val report = CoconutProductionReport(
                    estateId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COCONUT_ESTATE_ID_FR)),
                    plantDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COCONUT_PLANT_DATE)),
                    numberOfCoconuts = cursor.getInt(cursor.getColumnIndexOrThrow("TotalCoconuts")),
                    treeAge = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COCONUT_TREE_AGE)),
                    treeHealth = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TREE_HEALTH)),
                    treeType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COCONUT_TREE_TYPE)),
                    additionalInfo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COCONUT_ADDITIONAL_INFO))
                )
                productionList.add(report)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return productionList
    }

    // Database method to retrieve intercrop data
    fun getEstateWiseIntercropReport(): List<IntercropProductionReport> {
        val db = readableDatabase
        val reportList = mutableListOf<IntercropProductionReport>()

        // Updated query: Select the correct columns from the Intercrop table.
        // (Assuming that TABLE_ESTATE and Estate_ID are defined elsewhere)
        val query = """
        SELECT i.$COLUMN_INTERCROPS_ESTATE_ID_FR, 
               i.$COLUMN_INTERCROP_TYPE, 
               i.$COLUMN_INTERCROP_PLANTED_DATE, 
               i.$COLUMN_INTERCROP_ADDITIONAL_INFO
        FROM $TABLE_INTERCROP i
        JOIN $TABLE_ESTATE e ON i.$COLUMN_INTERCROPS_ESTATE_ID_FR = e.$Estate_ID
    """

        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                // Use the defined constants so that the column names match exactly.
                val estateId = cursor.getInt(cursor.getColumnIndex(COLUMN_INTERCROPS_ESTATE_ID_FR))
                val intercropType = cursor.getString(cursor.getColumnIndex(COLUMN_INTERCROP_TYPE))
                val plantedDate = cursor.getString(cursor.getColumnIndex(COLUMN_INTERCROP_PLANTED_DATE))
                val additionalInfo = cursor.getString(cursor.getColumnIndex(COLUMN_INTERCROP_ADDITIONAL_INFO))

                val report = IntercropProductionReport(
                    estateId = estateId,
                    intercropType = intercropType,
                    plantedDate = plantedDate,
                    additionalInfo = additionalInfo
                )
                reportList.add(report)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return reportList
    }


    fun getPendingOrdersCount(userId: Int): Int {
        val db = this.readableDatabase
        val query = """
            SELECT COUNT(*) 
            FROM $TABLE_BUYER_ORDER 
            WHERE $BUYER_USER_ID = ? 
              AND $BUYER_ORDER_STATUS NOT IN ('Delivered', 'Cancelled')
        """.trimIndent()
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count
    }

    fun getPendingTaskCount(estateId: Int?): Int {
        val db = this.readableDatabase
        val query = """
            SELECT COUNT(*) FROM $TABLE_TASK 
            WHERE $TASK_PROGRESS != ? AND $TASK_ESTATE_ID_FR = ?
        """.trimIndent()
        val args = arrayOf("Task Complete", estateId.toString())
        val cursor = db.rawQuery(query, args)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count
    }

    fun getNotDeliveredOrCancelledBuyerOrdersCount(): Int {
        val db = this.readableDatabase
        val query = """
        SELECT COUNT(*) FROM $TABLE_BUYER_ORDER 
        WHERE $BUYER_ORDER_STATUS NOT IN (?, ?)
    """.trimIndent()
        val args = arrayOf("Delivered", "Cancelled")
        val cursor = db.rawQuery(query, args)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count
    }


    fun getPendingSupplierOrdersCount(): Int {
        val db = this.readableDatabase
        val query = """
            SELECT COUNT(*) FROM $TABLE_SUPPLIER_ORDER 
            WHERE $SUPPLIER_FIELDMANAGER_STATUS = ?
        """.trimIndent()
        val args = arrayOf("Pending")
        val cursor = db.rawQuery(query, args)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count
    }

    fun getNonCancelledBuyerOrderCount(): Int {
        val db = this.readableDatabase
        // Query to count orders where status is not 'Cancelled'
        val query = "SELECT COUNT(*) FROM $TABLE_BUYER_ORDER WHERE $BUYER_ORDER_STATUS != ?"
        val cursor = db.rawQuery(query, arrayOf("Cancelled"))
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count
    }

    fun getSumOfNonCancelledBuyerOrderCost(): Double {
        val db = this.readableDatabase
        // Query to sum order costs for orders where status is not 'Cancelled'
        val query = "SELECT SUM($BUYER_ORDER_COST) FROM $TABLE_BUYER_ORDER WHERE $BUYER_ORDER_STATUS != ?"
        val cursor = db.rawQuery(query, arrayOf("Cancelled"))
        var sum = 0.0
        if (cursor.moveToFirst()) {
            // Use getDouble to retrieve the sum value. It will return null as 0.0 if no rows match.
            sum = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return sum
    }















}
