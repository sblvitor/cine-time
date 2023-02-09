package com.lira.cinetime.ui.popularMovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser
import com.lira.cinetime.R
import com.lira.cinetime.databinding.FragmentPopularMoviesBinding
import com.lira.cinetime.presentation.PopularMoviesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularMoviesFragment : Fragment() {

    private var _binding: FragmentPopularMoviesBinding? = null
    private val popularMoviesViewModel by viewModel<PopularMoviesViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPopularMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                popularMoviesViewModel.isConnected.collectLatest {
                    if(it == null){
                        val navController = findNavController()
                        navController.navigate(R.id.action_nav_popular_movies_to_navigation_login_flow)
                    } else{
                        updateNavHeader(it)
                    }
                }
            }
        }
    }

    private fun updateNavHeader(user: FirebaseUser) {
        val navView = requireActivity().findViewById<NavigationView>(R.id.nav_view)
        val header = navView.getHeaderView(0)
        val tvName: TextView = header.findViewById(R.id.tv_name)
        val tvEmail: TextView = header.findViewById(R.id.tv_email)

        tvName.text = user.displayName
        tvEmail.text = user.email

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}