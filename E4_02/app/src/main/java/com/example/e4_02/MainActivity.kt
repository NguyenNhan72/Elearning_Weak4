package com.example.e4_02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "forget_password") {

        //quen pass
        composable("forget_password") { backStackEntry ->
            // Lấy dữ liệu truyền ngược về từ màn 4
            val savedStateHandle = backStackEntry.savedStateHandle
            val resultEmail = savedStateHandle.get<String>("email") ?: ""
            val resultOtp = savedStateHandle.get<String>("otp") ?: ""
            val resultPass = savedStateHandle.get<String>("password") ?: ""

            ForgetPasswordScreen(
                resultEmail = resultEmail,
                resultOtp = resultOtp,
                resultPass = resultPass,
                onNext = { email ->
                    if(email.isNotEmpty()) navController.navigate("verify_code/$email")
                }
            )
        }

        //xac thuc
        composable("verify_code/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerifyCodeScreen(
                email = email,
                onNext = { otp ->
                    if(otp.isNotEmpty()) navController.navigate("reset_password/$email/$otp")
                },
                onBack = { navController.popBackStack() }
            )
        }

        //tao pass moi
        composable("reset_password/{email}/{otp}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val otp = backStackEntry.arguments?.getString("otp") ?: ""
            ResetPasswordScreen(
                email = email,
                otp = otp,
                onNext = { pass ->
                    if(pass.isNotEmpty()) navController.navigate("confirm/$email/$otp/$pass")
                },
                onBack = { navController.popBackStack() }
            )
        }

        //xac nhan
        composable("confirm/{email}/{otp}/{password}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val otp = backStackEntry.arguments?.getString("otp") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""

            ConfirmScreen(
                email = email,
                otp = otp,
                password = password,
                onSubmit = {
                    // Truyền dữ liệu ngược về màn 1 và xóa lịch sử các màn trung gian
                    navController.getBackStackEntry("forget_password").savedStateHandle.apply {
                        set("email", email)
                        set("otp", otp)
                        set("password", password)
                    }
                    navController.popBackStack("forget_password", inclusive = false)
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreen(
    resultEmail: String,
    resultOtp: String,
    resultPass: String,
    onNext: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("UTH SmartTasks", style = MaterialTheme.typography.headlineMedium, color = Color.Blue)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Forget Password?", fontWeight = FontWeight.Bold)
        Text("Enter your Email, we will send you a verification code.")

        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Your Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { onNext(email) }, modifier = Modifier.fillMaxWidth()) {
            Text("Next")
        }

        if (resultEmail.isNotEmpty()) {
            Spacer(modifier = Modifier.height(32.dp))
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Dữ liệu từ màn hình 4:", color = Color.Red)
                    Text("Email: $resultEmail")
                    Text("OTP: $resultOtp")
                    Text("Pass: $resultPass")
                }
            }
        }
    }
}

@Composable
fun VerifyCodeScreen(email: String, onNext: (String) -> Unit, onBack: () -> Unit) {
    var otp by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") }
        }
        Text("UTH SmartTasks", style = MaterialTheme.typography.headlineMedium, color = Color.Blue)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Verify Code", fontWeight = FontWeight.Bold)
        Text("Sent to $email")

        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text("Enter OTP") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { onNext(otp) }, modifier = Modifier.fillMaxWidth()) {
            Text("Next")
        }
    }
}

@Composable
fun ResetPasswordScreen(email: String, otp: String, onNext: (String) -> Unit, onBack: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") }
        }
        Text("UTH SmartTasks", style = MaterialTheme.typography.headlineMedium, color = Color.Blue)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Create new password", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { onNext(password) }, modifier = Modifier.fillMaxWidth()) {
            Text("Next")
        }
    }
}

@Composable
fun ConfirmScreen(email: String, otp: String, password: String, onSubmit: () -> Unit, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Back") }
        }
        Text("UTH SmartTasks", style = MaterialTheme.typography.headlineMedium, color = Color.Blue)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Confirm", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(value = email, onValueChange = {}, readOnly = true, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = otp, onValueChange = {}, readOnly = true, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = {}, readOnly = true, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onSubmit, modifier = Modifier.fillMaxWidth()) {
            Text("Submit")
        }
    }
}