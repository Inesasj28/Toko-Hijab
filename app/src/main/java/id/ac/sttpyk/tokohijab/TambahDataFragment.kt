package id.ac.sttpyk.tokohijab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import id.ac.sttpyk.tokohijab.api.Api
import id.ac.sttpyk.tokohijab.databinding.FragmentTambahDataBinding
import id.ac.sttpyk.tokohijab.model.SimpanModel
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_tambah_data.view.*
import kotlinx.android.synthetic.main.item_hijab.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahDataFragment : Fragment() {

    private lateinit var binding: FragmentTambahDataBinding
    val api by lazy{ Api.create()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTambahDataBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputnamahijab.setText(hijab?.namahijab.toString())
        binding.inputharga.setText(hijab?.harga.toString())
        binding.btnSimpan.setOnClickListener {
            if (hijab!=null){
                editdata()
            }else{
                simpandata()
            }
        }

        binding.cancel.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun simpandata() {
        val pupuk = binding.inputnamahijab.text.toString()
        val jumlah = binding.inputharga.text.toString()
        api.entrihijab(pupuk, jumlah).enqueue(object : Callback<SimpanModel>{
            override fun onResponse(call: retrofit2.Call<SimpanModel>, response: Response<SimpanModel>){
                Toast.makeText(requireContext(), "Data Berhasi di Simpan", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: retrofit2.Call<SimpanModel>, t: Throwable) {
                Toast.makeText(requireContext(), "Error : ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun editdata(){
        val id = hijab?.id
        val hijab = binding.inputnamahijab.text.toString()
        val harga = binding.inputharga.text.toString()

        api.edithijab(id, hijab, harga).enqueue(object :Callback<SimpanModel>{
            override fun onResponse(call: Call<SimpanModel>, response: Response<SimpanModel>) {
                val result = response.body()
                if (result?.response_code==200){
                    Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    requireActivity().onBackPressed()
                }else{
                    Toast.makeText(requireContext(),result?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SimpanModel>, t: Throwable) {
                Toast.makeText(requireContext(), "Error ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}