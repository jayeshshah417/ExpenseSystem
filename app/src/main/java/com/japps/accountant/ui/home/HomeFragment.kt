package com.japps.accountant.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.japps.accountant.R
import com.japps.accountant.adapters.RecyclerViewAdapter
import com.japps.accountant.databinding.FragmentHomeBinding
import com.japps.accountant.models.AccountModel
import com.japps.accountant.models.AccountTagModel
import com.japps.accountant.room_db.AccountantViewModel
import com.japps.accountant.ui.dialog.NewAccountDialog
import com.japps.accountant.ui.dialog.NewAccountEntryDialog
import com.japps.accountant.ui.dialog.NewAccountTagDialog
import com.japps.accountant.utils.Utility
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(),RecyclerViewAdapter.RowOnClickListener {

    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(activity, R.anim.rotate_open_anim) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(activity, R.anim.rotate_close_anim) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(activity, R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(activity, R.anim.to_bottom_anim) }
    private var clicked = false
    private var _binding: FragmentHomeBinding? = null
    private lateinit var accountantViewModel: AccountantViewModel
    private lateinit var listResources:ArrayList<String>
    enum class TYPE{
        ACCOUNT,ACCOUNT_ENTRY,ACCOUNT_TAG
    }

    private var listTag:List<AccountTagModel> = ArrayList()
    private var listAccount : List<AccountModel> = ArrayList()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        listResources = ArrayList<String>(Arrays.asList(*resources.getStringArray(R.array.items)))
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.addItem.setOnClickListener {
            onAddItemClick()
        }

        val items_array = ArrayList<String>(Arrays.asList(*resources.getStringArray(R.array.items)))
        val recyclerViewAdapter = RecyclerViewAdapter(items_array,this)
        binding.rvData.layoutManager = LinearLayoutManager(activity)
        binding.rvData.adapter = recyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()

        binding.addItemAccount.setOnClickListener(View.OnClickListener {
            val accountDialog = NewAccountDialog(requireContext(),accountantViewModel,listTag)
            accountDialog.show()
            val accWindow = accountDialog.window
            accWindow!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        })


        binding.addItemAccountEntry.setOnClickListener(View.OnClickListener {
            val accountDialog = NewAccountEntryDialog(requireContext(),accountantViewModel,requireActivity()!!.supportFragmentManager,listAccount)
            accountDialog.show()
            val accWindow = accountDialog.window
            accWindow!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        })

        binding.addItemAccountTag.setOnClickListener(View.OnClickListener {
            val accountDialog = NewAccountTagDialog(requireContext(),accountantViewModel)
            accountDialog.show()
            val accWindow = accountDialog.window
            accWindow!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        })
        accountantViewModel = ViewModelProvider(requireActivity()).get(AccountantViewModel::class.java)

        accountantViewModel.getAllAccount().observe(requireActivity(), androidx.lifecycle.Observer {
            listAccount = it
            Log.d("Size Acc ",it.size.toString())
            val new  = listResources?.get(0)+" ("+it.size+")"
            items_array.removeAt(0)
            items_array.add(0,new)
            recyclerViewAdapter.notifyDataSetChanged()
        })
        accountantViewModel.getAllAccountEntry().observe(requireActivity(), androidx.lifecycle.Observer {
            Log.d("Size Acc Entry",it?.size.toString())
            val new  = listResources?.get(1)+" ("+it.size+")"
            items_array.removeAt(1)
            items_array.add(1,new)
            recyclerViewAdapter.notifyDataSetChanged()
        })
        accountantViewModel.getAllTags().observe(requireActivity(), androidx.lifecycle.Observer {
            listTag = it
            Log.d("Size tag",it.size.toString())
            val new  =listResources?.get(2)+" ("+it.size+")"
            items_array.removeAt(2)
            items_array.add(2,new)
            recyclerViewAdapter.notifyDataSetChanged()
        })

        return root
    }

    private fun onAddItemClick() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            binding.addItemAccount.visibility = View.VISIBLE
            binding.addItemAccountEntry.visibility = View.VISIBLE
            binding.addItemAccountTag.visibility = View.VISIBLE
        }else{
            binding.addItemAccount.visibility = View.GONE
            binding.addItemAccountEntry.visibility = View.GONE
            binding.addItemAccountTag.visibility = View.GONE
        }
    }

    private fun setAnimation(clicked: Boolean){
        if(!clicked){
            binding.addItemAccount.startAnimation(fromBottom)
            binding.addItemAccountEntry.startAnimation(fromBottom)
            binding.addItemAccountTag.startAnimation(fromBottom)
            binding.addItem.startAnimation(rotateOpen)
        }else{
            binding.addItemAccountTag.startAnimation(toBottom)
            binding.addItemAccountEntry.startAnimation(toBottom)
            binding.addItemAccount.startAnimation(toBottom)
            binding.addItem.startAnimation(rotateClose)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRowClick(position: Int) {
        context?.let { Utility.getScreen(it,position) }
    }
}