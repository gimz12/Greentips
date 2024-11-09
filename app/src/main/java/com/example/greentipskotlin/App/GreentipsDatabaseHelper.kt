import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.greentipskotlin.App.Model.Buyer
import com.example.greentipskotlin.App.Model.Coconut
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.App.Model.EmployeePosition
import com.example.greentipskotlin.App.Model.Estate
import com.example.greentipskotlin.App.Model.HarvestInfo
import com.example.greentipskotlin.App.Model.Intercrops
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
        FOREIGN KEY($COLUMN_EMPLOYEE_POSITION_ID_FR) REFERENCES $TABLE_EMPLOYEE_POSITION($EMPLOYEE_POSITION_ID)
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

        db.execSQL(createEstateTable)
        db.execSQL(createEmployeePositionTable)
        db.execSQL(createEmployeeTable)
        db.execSQL(createCoconutTable)
        db.execSQL(createBuyerTable)
        db.execSQL(createIntercropTable)
        db.execSQL(createHarvestTable)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLOYEE_POSITION")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLOYEE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ESTATE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COCONUT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BUYER")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_INTERCROP")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_HARVEST_INFO")
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

                // Add employee to the list
                employees.add(Employee(name, phoneNumber, address, gender, joinDate, dateOfBirth, age, username, email, password, positionId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return employees
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
}
