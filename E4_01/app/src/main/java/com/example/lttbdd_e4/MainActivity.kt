package com.example.lttbdd_e4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LibraryManagementScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryManagementScreen() {
    var studentName by remember { mutableStateOf("Nguyễn Trọng Nhân") }
    var book1Checked by remember { mutableStateOf(true) }
    var book2Checked by remember { mutableStateOf(true) }
    var book3Checked by remember { mutableStateOf(true) }
    var book4Checked by remember { mutableStateOf(true) }
    var book5Checked by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Quản lý") },
                    label = { Text("Quản lý") },
                    selected = true,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "DS Sách") },
                    label = { Text("DS Sách") },
                    selected = false,
                    onClick = { }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Sinh viên") },
                    label = { Text("Sinh viên") },
                    selected = false,
                    onClick = { }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //tieu de
            Text(
                text = "Hệ thống\nQuản lý Thư viện",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            //nhap ten sinh vien
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Sinh viên", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = studentName,
                        onValueChange = { studentName = it },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C59B9))
                    ) {
                        Text("Thay đổi")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            //danh sạch
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Danh sách sách", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    BookCheckboxRow(
                        title = "Sách 01",
                        isChecked = book1Checked,
                        onCheckedChange = { book1Checked = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BookCheckboxRow(
                        title = "Sách 02",
                        isChecked = book2Checked,
                        onCheckedChange = { book2Checked = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BookCheckboxRow(
                        title = "Sách 03",
                        isChecked = book3Checked,
                        onCheckedChange = { book3Checked = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BookCheckboxRow(
                        title = "Sách 04",
                        isChecked = book4Checked,
                        onCheckedChange = { book4Checked = it }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BookCheckboxRow(
                        title = "Sách 05",
                        isChecked = book5Checked,
                        onCheckedChange = { book5Checked = it }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            //them
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C59B9))
            ) {
                Text("Thêm", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun BookCheckboxRow(title: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = Color(0xFFA52A2A))
        )
        Text(text = title, modifier = Modifier.padding(start = 8.dp))
    }
}