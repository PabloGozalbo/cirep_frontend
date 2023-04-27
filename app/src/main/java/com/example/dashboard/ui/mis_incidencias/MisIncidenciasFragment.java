package com.example.dashboard.ui.mis_incidencias;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cirep_frontend.databinding.FragmentIncidenciasBinding;


public class MisIncidenciasFragment extends Fragment {

    private FragmentIncidenciasBinding binding;
    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private IncidenciasViewModel viewModel;

    /*
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MisIncidenciasViewModel slideshowViewModel =
                new ViewModelProvider(this).get(MisIncidenciasViewModel.class);

        this.viewModel = new IncidenciasViewModel();


        binding = FragmentIncidenciasBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // 1. Obtener la referencia al RecyclerView desde el layout
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // 2. Configurar el LayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 3. Crear el adaptador y asignarlo al RecyclerView
        mPostAdapter = new PostAdapter(this.getContext(), viewModel.getIncidenciasUsuario());
        mRecyclerView.setAdapter(mPostAdapter);

        // 4. Agregar un escuchador para el click en cada elemento
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Acción a realizar cuando se hace click en un elemento
            }
        }));

        return rootView;
    }
    */

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void observeRegistrationSuccess() {
        viewModel.getIncidenciasUserSuccess().observe(this, registrationSuccess -> {

            //TODO: cambiar para que se controle la logica
            if (registrationSuccess == null) {

            } else {

            }
        });
    }
}