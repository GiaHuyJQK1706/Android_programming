package com.example.formvnbasic

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    // Khai báo các thành phần giao diện
    private lateinit var etMSSV: EditText
    private lateinit var etHoTen: EditText
    private lateinit var etEmail: EditText
    private lateinit var etSoDienThoai: EditText
    private lateinit var rbgGen: RadioGroup
    private lateinit var cbDongY: CheckBox
    private lateinit var btnChonNgay: Button
    private lateinit var calendarView: CalendarView
    private lateinit var spTinhThanh: Spinner
    private lateinit var spQuanHuyen: Spinner
    private lateinit var spPhuongXa: Spinner
    private lateinit var btnSubmit: Button

    private lateinit var addressHelper: AddressHelper // Khởi tạo AddressHelper
    private var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo AddressHelper
        addressHelper = AddressHelper(resources)

        // Ánh xạ các thành phần giao diện
        etMSSV = findViewById(R.id.etMSSV)
        etHoTen = findViewById(R.id.etHoTen)
        etEmail = findViewById(R.id.etEmail)
        etSoDienThoai = findViewById(R.id.etSoDienThoai)
        rbgGen = findViewById(R.id.rbgGen)
        cbDongY = findViewById(R.id.cbDongY)
        btnChonNgay = findViewById(R.id.btnChonNgay)
        calendarView = findViewById(R.id.calendarView)
        spTinhThanh = findViewById(R.id.spTinhThanh)
        spQuanHuyen = findViewById(R.id.spQuanHuyen)
        spPhuongXa = findViewById(R.id.spPhuongXa)
        btnSubmit = findViewById(R.id.btnSubmit)

        // Thiết lập Spinner Tỉnh/Thành
        setupProvinceSpinner()

        // Sự kiện chọn ngày sinh
        btnChonNgay.setOnClickListener(View.OnClickListener{ v: View? ->
            calendarView.setVisibility(
                if (calendarView.getVisibility() == View.GONE) View.VISIBLE else View.GONE
            )
        })
        calendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener{ view: CalendarView?, year: Int, month: Int, dayOfMonth: Int ->
            selectedDate = dayOfMonth.toString() + "/" + (month + 1) + "/" + year
            btnChonNgay.setText("Ngày sinh: $selectedDate")
            calendarView.setVisibility(View.GONE)
        })

        // Xử lý khi nhấn Submit
        btnSubmit.setOnClickListener {
            if (kiemTraThongTin()) {
                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setupProvinceSpinner() {
        // Lấy danh sách Tỉnh/Thành từ AddressHelper
        val provinces = addressHelper.getProvinces()
        val provinceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTinhThanh.adapter = provinceAdapter

        spTinhThanh.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedProvince = provinces[position]
                setupDistrictSpinner(selectedProvince) // Cập nhật danh sách Quận/Huyện
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupDistrictSpinner(province: String) {
        // Lấy danh sách Quận/Huyện từ AddressHelper
        val districts = addressHelper.getDistricts(province)
        val districtAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, districts)
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spQuanHuyen.adapter = districtAdapter

        spQuanHuyen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDistrict = districts[position]
                setupWardSpinner(province, selectedDistrict) // Cập nhật danh sách Phường/Xã
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupWardSpinner(province: String, district: String) {
        // Lấy danh sách Phường/Xã từ AddressHelper
        val wards = addressHelper.getWards(province, district)
        val wardAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, wards)
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPhuongXa.adapter = wardAdapter
    }

    private fun kiemTraThongTin(): Boolean {
        // Kiểm tra thông tin người dùng
        return etMSSV.text.isNotEmpty() && etHoTen.text.isNotEmpty() &&
                etEmail.text.isNotEmpty() && etSoDienThoai.text.isNotEmpty() &&
                rbgGen.checkedRadioButtonId != -1 && cbDongY.isChecked
    }
}
