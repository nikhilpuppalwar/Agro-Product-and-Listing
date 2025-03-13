package pccoe.pune.agroproduct.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pccoe.pune.agroproduct.Adapter.PopularAdapter
import pccoe.pune.agroproduct.DataClass.ItemsModel
import pccoe.pune.agroproduct.R
import pccoe.pune.agroproduct.databinding.FragmentHome2Binding


class HomeFragment : Fragment() {
    private lateinit var adapter: PopularAdapter
    private lateinit var binding: FragmentHome2Binding
    private val _popular = MutableLiveData<MutableList<ItemsModel>>()
    val popular: MutableLiveData<MutableList<ItemsModel>> get() = _popular
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHome2Binding.inflate(inflater,container,false)

        binding.imagViewall.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_layout, ExplorlarFragment()) // Replace with the correct container ID
                .addToBackStack(null) // Adds it to back stack to allow back navigation
                .commit()


            val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
            bottomNav.selectedItemId = R.id.navigation_Explore
        }

           init()


        return binding.root
    }
private fun init(){
    popular.observe(viewLifecycleOwner){data ->

    binding.recyclerview.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerview.adapter = PopularAdapter(data)
}
        loadPopular()
}

   private fun loadPopular(){
        val Ref = firebaseDatabase.getReference("Items")
        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()
                    for (childSnapshot in snapshot.children){
                        val list = childSnapshot.getValue(ItemsModel::class.java)
                        if (list != null){
                            lists.add(list)
                        }
                    }
            _popular.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "error:"+ error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}