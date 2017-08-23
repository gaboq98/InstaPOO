package layout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gaboq.instapoo.BaseFragment;
import com.example.gaboq.instapoo.MainActivity;
import com.example.gaboq.instapoo.PhotoAdapter;
import com.example.gaboq.instapoo.PhotoGalleryAsyncLoader;
import com.example.gaboq.instapoo.R;
import com.example.gaboq.instapoo.PhotoItem;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends BaseFragment implements AbsListView.OnItemClickListener,
        LoaderManager.LoaderCallbacks<List<PhotoItem>> {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected OnFragmentInteractionListener mListener;
    protected AbsListView mListView;
    protected PhotoAdapter mAdapter;
    protected ArrayList<PhotoItem> mPhotoListItem;
    protected TextView mEmptyTextView;
    protected ProgressDialog mLoadingProgressDialog;
    public GalleryFragment() {
        // Required empty public constructor
    }


    //public static GalleryFragment newInstance(String param1, String param2) {
    //    GalleryFragment fragment = new GalleryFragment();
    //    Bundle args = new Bundle();
    //    args.putString(ARG_PARAM1, param1);
    //    args.putString(ARG_PARAM2, param2);
    //    fragment.setArguments(args);
    //    return fragment;
    //}

    public static GalleryFragment newInstance(int sectionNumber) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Context context = getActivity().getBaseContext();

        mPhotoListItem = new ArrayList() ;
        mAdapter = new PhotoAdapter(context,
                R.layout.photo_image,
                mPhotoListItem, false);

        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = null;
        view = inflater.inflate(R.layout.fragment_gallery, container, false);

        // Set the mAdapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        mEmptyTextView = (TextView)view.findViewById(R.id.empty);

        // Show the empty text / message.
        resolveEmptyText();

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;


    }

    /**
     * Used to show a generic empty text warning. Override in inheriting classes.
     */
    protected void resolveEmptyText(){
        if(mAdapter.isEmpty()){
            mEmptyTextView.setVisibility(View.VISIBLE);
            setEmptyText();
        } else {
            mEmptyTextView.setVisibility(View.INVISIBLE);
        }
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnFragmentInteractionListener) activity;
            // Show a progress dialog.
            mLoadingProgressDialog = new ProgressDialog(getActivity());
            mLoadingProgressDialog.setMessage("Loading Photos...");
            mLoadingProgressDialog.setCancelable(true);
            mLoadingProgressDialog.show();
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        cancelProgressDialog();

    }

    @Override
    public void onPause(){
        super.onPause();
        cancelProgressDialog();
    }

    @Override
    public void onStop(){
        super.onStop();
        cancelProgressDialog();
    }


    @Override
    public Loader<List<PhotoItem>> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // sample only has one Loader with no arguments, so it is simple.
        return new Loader<>(getActivity());
    }


    @Override
    public void onLoadFinished(Loader<List<PhotoItem>> loader, List<PhotoItem> data) {

        mPhotoListItem.clear();

        for(int i = 0; i < data.size();i++){
            PhotoItem item = data.get(i);
            mPhotoListItem.add(item);
        }

        mAdapter.notifyDataSetChanged();
        resolveEmptyText();
        cancelProgressDialog();

    }


    @Override
    public void onLoaderReset(Loader<List<PhotoItem>> loader) {
        mPhotoListItem.clear();
        mAdapter.notifyDataSetChanged();
        resolveEmptyText();
        cancelProgressDialog();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Tell the share builder to add the photo to the share operation.
            PhotoItem photoListItem = (PhotoItem)this.mAdapter.getItem(position);
            String imagePath = photoListItem.getThumbnailUri().getPath();
            mListener.onFragmentInteraction(MainActivity.SELECT_PHOTO_ACTION);
            resetFragmentState();
        }

    }

    protected void resetFragmentState(){
        // Clear view state
        getActivity().invalidateOptionsMenu();
        ((BaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int uri);
    }

    public void setEmptyText() {
        mEmptyTextView.setText("No Photos!");
    }

    private void cancelProgressDialog(){
        if(mLoadingProgressDialog != null){
            if(mLoadingProgressDialog.isShowing()){
                mLoadingProgressDialog.cancel();
            }
        }
    }

}
