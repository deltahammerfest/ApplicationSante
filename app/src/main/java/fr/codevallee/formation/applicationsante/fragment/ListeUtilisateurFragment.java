package fr.codevallee.formation.applicationsante.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fr.codevallee.formation.applicationsante.AddUserActivity;
import fr.codevallee.formation.applicationsante.R;
import fr.codevallee.formation.applicationsante.User;
import fr.codevallee.formation.applicationsante.UserAdapter;
import fr.codevallee.formation.applicationsante.UserDAO;
import fr.codevallee.formation.applicationsante.UserDataSource;

public class ListeUtilisateurFragment extends ListFragment {

    OnHeadlineSelectedListener mCallback;

    public interface OnHeadlineSelectedListener {
        public void onUserSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Création de la base et du DAO :
        UserDataSource userDataSource = new UserDataSource(getContext());
        UserDAO userDAO = new UserDAO(userDataSource);

        ////TEST
        //Remplissage:
        User user1 = new User("Jean","Patrick","Homme","Infirmier","Neurologie","jp@hmail.com","0102030405","Fait des trucs");
        User user2 = new User("Lazaro","Guillaume","Homme","Médecin","Neurologie","gl@hmail.com","0607080910","Fait parfois des trucs");

        /*final ArrayList<User> listUser = new ArrayList<>();
        listUser.add(user1);
        listUser.add(user2);*/

        ////Jusque là

        userDAO.create(user1);
        userDAO.create(user2);


        //ArrayList<String> listName = new ArrayList<>();
        ArrayList<User> listUser = (ArrayList<User>) userDAO.readAll();

        /*for (int i=0 ; i<listUser.size() ; i++) {
            listName.add(i, listUser.get(i).getNom()+" "+listUser.get(i).getPrenom());
        }*/

        UserAdapter userAdapter = new UserAdapter(getContext(), listUser);

        setListAdapter(userAdapter);

        Log.d("Test","Taille de la liste "+listUser.size());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        /*if(getFragmentManager().findFragmentById(R.id.fragment_user) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_liste_utilisateur, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO : ajouter l'accés à l'activity createUser
                Intent intent = new Intent(getActivity(), AddUserActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Send the event to the host activity
        mCallback.onUserSelected(position);
    }
}
