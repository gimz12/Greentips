import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.greentipskotlin.App.Model.Employee
import com.example.greentipskotlin.App.Model.EmployeePosition

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

        db.execSQL(createEmployeePositionTable)
        db.execSQL(createEmployeeTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLOYEE_POSITION")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLOYEE")
        onCreate(db)
    }

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
    }
}
