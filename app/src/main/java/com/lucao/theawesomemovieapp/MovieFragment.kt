package com.lucao.theawesomemovieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.lucao.theawesomemovieapp.databinding.FragmentMovieItemBinding
import com.lucao.theawesomemovieapp.placeholder.PlaceholderContent

class MovieFragment : Fragment(), MovieItemListener {

    private lateinit var adapter: MyItemRecyclerViewAdapter
    private val viewModel by navGraphViewModels<MovieViewModel>(R.id.movie_graph) { defaultViewModelProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovieItemBinding.inflate(inflater)
        val view = binding.root as RecyclerView
        adapter = MyItemRecyclerViewAdapter(this)
        setupview(view)
        initObservers()
        return view
    }

    private fun setupview(view: RecyclerView) {
        view.apply {
            this.adapter = this@MovieFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initObservers() {
        viewModel.movieListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
        })

        viewModel.navigationToMovieDetailsLiveData.observe(viewLifecycleOwner, Observer {
            val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment()
            findNavController().navigate(action)
        })
    }

    override fun onItemSelected(position: Int) {
        viewModel.onMovieSelected(position)
    }
}