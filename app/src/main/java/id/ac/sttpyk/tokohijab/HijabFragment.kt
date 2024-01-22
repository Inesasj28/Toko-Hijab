import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.sttpyk.tokohijab.R
import id.ac.sttpyk.tokohijab.api.Api
import id.ac.sttpyk.tokohijab.databinding.FragmentHijabBinding
import id.ac.sttpyk.tokohijab.helper.AdapterHijab
import id.ac.sttpyk.tokohijab.model.HijabModel
import id.ac.sttpyk.tokohijab.model.SimpanModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HijabFragment : Fragment() {

    private lateinit var binding : FragmentHijabBinding
    private val api by lazy{ Api.create()}
    private val navController  by lazy { findNavController() }

    companion object{
        const val HIJAB= "HIJAB"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHijabBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        api.tampilhijab().enqueue(object : Callback<HijabModel>{
            override fun onResponse(call: Call<HijabModel>, response: Response<HijabModel>) {
                if (response.isSuccessful){
                    Log.e("TAG", "onResponse: ${response.body()}",)
                    val result = response.body()!!.data
                    binding.listdatahijab.layoutManager =
                        LinearLayoutManager(requireContext())
                    binding.listdatahijab.itemAnimator = null
                    val adapterHijab = AdapterHijab(result as ArrayList<HijabModel.Data>,
                        object : AdapterHijab.OnAdapterListener {
                            override fun OnClick(hijab : HijabModel.Data) {
                                var bundle = Bundle().apply {
                                    putParcelable(HIJAB, hijab)
                                }
                                navController.navigate(R.id.action_hijabFragment_to_tambahDataFragment,bundle)
                            }
                        })
                    adapterHijab.setOnItemClickCallback {
                        api.hapushijab(it.id).enqueue(object : Callback<SimpanModel>{
                            override fun onResponse(call: Call<SimpanModel>, response: Response<SimpanModel>
                            ) {
                                val submit = response.body()
                                if (response.isSuccessful){
                                    Toast.makeText(requireContext(), submit!!.message, Toast.LENGTH_SHORT).show()
                                    val deleteindex = result.indexOf(it)
                                    result.removeAt(deleteindex)
                                    adapterHijab.notifyItemChanged(deleteindex)
                                }
                            }

                            override fun onFailure(call: Call<SimpanModel>, t: Throwable) {
                                Toast.makeText(requireContext(), "Error : ${t.message}", Toast.LENGTH_SHORT).show()
                            }

                        })
                    }
                    binding.listdatahijab.adapter = adapterHijab
                }
            }

            override fun onFailure(call: Call<HijabModel>, t: Throwable) {
                Toast.makeText(requireContext(),"Error : ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        binding.kembali.setOnClickListener{
            requireActivity().onBackPressed()
        }
        binding.tambah.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_hijabFragment_to_tambahDataFragment)
        )
    }

}