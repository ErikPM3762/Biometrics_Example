package com.example.biometricsexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.biometricsexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var canAuthenticate = false
    lateinit var prompInfo: BiometricPrompt.PromptInfo
    private var auth = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAuth()

        binding.btnBiometrics.setOnClickListener {
            authenticate {
                auth = it }
        }
    }

    private fun setupAuth() {
        if(BiometricManager.from(this).canAuthenticate(BIOMETRIC_STRONG
                    or DEVICE_CREDENTIAL ) == BiometricManager.BIOMETRIC_SUCCESS)
            canAuthenticate = true
        prompInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticacion Biometrica") //Esta parte del codigo es opcional, y puede ser modificada por el desarrollador.
            .setSubtitle("Autenticate con el sensor biometrico") //Esta parte del codigo es opcional, y puede ser modificada por el desarrollador.
            .setAllowedAuthenticators(BIOMETRIC_STRONG
                    or DEVICE_CREDENTIAL)
            .build()
    }

    private fun authenticate(auth:(auth:Boolean) -> Unit){
        if (canAuthenticate){
            BiometricPrompt(this, ContextCompat.getMainExecutor(this),
                object : BiometricPrompt.AuthenticationCallback() {

                    //En la parte de onAuthenticationSucceeded colocar la acción que queremos que realice si fue correcto, faltan dos métodos mas; el error y el failed
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        auth(true)
                       Toast.makeText(this@MainActivity,"Se ejecuto el Biometrico.",Toast.LENGTH_SHORT).show()
//En este ejemplo estamos permitiendole al usuario pasar a un nuevo fragmento.
//La parte del success debe de ser configurada para su uso.
                    }
                }).authenticate(prompInfo)
        }else {
            auth(true)
        }

    }
}