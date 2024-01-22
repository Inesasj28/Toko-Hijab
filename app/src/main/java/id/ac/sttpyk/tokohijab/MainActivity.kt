package id.ac.sttpyk.tokohijab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dikodei.sipasan.helper.SessionManager
import id.ac.sttpyk.tokohijab.api.Api
import id.ac.sttpyk.tokohijab.model.LoginModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val api: Api by lazy { Api.create() }
    val session: SessionManager by lazy { SessionManager.init(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        ceklogin()

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login)
        val register = findViewById<TextView>(R.id.register)

        loginButton.setOnClickListener {
            val username: String = usernameEditText.text.toString()
            val password: String = passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this, "username dan password tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (username.length < 5 || password.length < 5) {
                Toast.makeText(
                    this, "username dan password minimal 5 karakter",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                api.login(username, password).enqueue(object : Callback<LoginModel> {
                    override fun onResponse(
                        call: Call<LoginModel>,
                        response: Response<LoginModel>
                    ) {
                        val loginModel: LoginModel? = response.body()
                        if (response.isSuccessful && loginModel != null) {
                            if (loginModel.response_code == 200) {
                                session.loginSession = loginModel
                                kehalamanmenu()
                                Toast.makeText(
                                    this@MainActivity,
                                    loginModel.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@MainActivity,
                                    loginModel.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                        Toast.makeText(
                            this@MainActivity,
                            "error : ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }
        }

        register.setOnClickListener {
            Toast.makeText(this, "register dlu ya", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }


    private fun ceklogin() {
        if (session.isLoggin) {
            val role = session.loginSession!!.conntent.id_role
            if (role == 1) {
                kehalamanmenu()
            } else {
                Toast.makeText(this, "Anda tidak berhak mengakses aplikasi ini", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun kehalamanmenu() {
        startActivity(Intent(this, MenuActivity::class.java))
        this.finish()
    }


}