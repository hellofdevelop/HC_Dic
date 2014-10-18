package org.hellofdevelp.hc_dic;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;

public class HeroListFragment extends BaseFragment
	implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
	
	private static final String TAG = HeroListFragment.class.getSimpleName();
	private static final boolean DEBUG = Const.DEBUG;
	

	private static final int kREQUEST_RESOLVE_ERROR = 1001;
	private static final String kDIALOG_ERROR = "dialog_error";
	
	
	private GoogleApiClient mGoogleApiClient = null;
	private boolean mResolvingError = false;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_hero_list, container, false);
		
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		buildGoogleApiClient();
	}

	@Override
	public void onResume() {
		super.onResume();
		
		if (mResolvingError == false) {
			mGoogleApiClient.connect();
		}
	}

	@Override
	public void onPause() {
		mGoogleApiClient.disconnect();
		
		super.onPause();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (DEBUG) Log.d(TAG, "requestCode=" + requestCode + " resultCode=" + resultCode + " data=" + data);

		switch (requestCode) {
		case kREQUEST_RESOLVE_ERROR:
			switch (resultCode) {
			case Activity.RESULT_OK:
				// Make sure the app is not already connected or attempting to connect
				if (!mGoogleApiClient.isConnecting()
						&& !mGoogleApiClient.isConnected()) {
					mGoogleApiClient.connect();
				}
				break;
				
			default:
				Log.e(TAG, "unknown resultCode=" + resultCode);
				break;
			}
			break;
			
		default:
			Log.e(TAG, "unknown requestCode=" + requestCode);
			break;
		}
	}

	
	@Override
	public void onConnected(Bundle connectionHint) {
		if (DEBUG) Log.d(TAG, "connectionHint=" + connectionHint);
		
		loadFile("Project HC_Dic");
	}

	@Override
	public void onConnectionSuspended(int cause) {
		if (DEBUG) Log.d(TAG, "cause=" + cause);
		
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (DEBUG) Log.d(TAG, "result=" + result);

		if (mResolvingError) {
			// Already attempting to resolve an error.
			return;
		}
		
		if (result.hasResolution()) {
			try {
				mResolvingError = true;
				
				result.startResolutionForResult(this.getActivity(), kREQUEST_RESOLVE_ERROR);
			} catch (SendIntentException e) {
				Log.e(TAG, "", e);
				
				// There was an error with the resolution intent. Try again.
				mGoogleApiClient.connect();
			}
		} else {
			Log.e(TAG, "result.getErrorCode=" + result.getErrorCode());
			
			// Show dialog using GooglePlayServicesUtil.getErrorDialog()
			//showErrorDialog(result.getErrorCode());
			mResolvingError = true;
		}
	}

	
	private void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity())
			.addApi(Drive.API)
			.addScope(Drive.SCOPE_FILE)
			.addConnectionCallbacks(this)
			.addOnConnectionFailedListener(this)
			.build();
	}

	/*
	// Creates a dialog for an error message
	private void showErrorDialog(int errorCode) {
		// Create a fragment for the error dialog
		ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
		// Pass the error that should be displayed
		Bundle args = new Bundle();
		args.putInt(kDIALOG_ERROR, errorCode);
		dialogFragment.setArguments(args);
		dialogFragment.show(getFragmentManager(), "errordialog");
	}

	// Called from ErrorDialogFragment when the dialog is dismissed.
	public void onDialogDismissed() {
		mResolvingError = false;
	}

	// A fragment to display an error dialog
	public static class ErrorDialogFragment extends DialogFragment {
		
		public ErrorDialogFragment() {
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Get the error code and retrieve the appropriate dialog
			int errorCode = this.getArguments().getInt(kDIALOG_ERROR);
			return GooglePlayServicesUtil.getErrorDialog(errorCode,
					this.getActivity(), kREQUEST_RESOLVE_ERROR);
		}

		@Override
		public void onDismiss(DialogInterface dialog) {
			((MainActivity) getActivity()).onDialogDismissed();
		}
		
	}
	*/
	
	private void loadFile(String fileName) {
		if (DEBUG) Log.d(TAG, "fileName=" + fileName);
		
		// Create a query for a specific filename in Drive.
	    Query query = new Query.Builder()
	            .addFilter(Filters.eq(SearchableField.TITLE, fileName))
	            .build();
	    
	    // Invoke the query asynchronously with a callback method
	    Drive.DriveApi.query(mGoogleApiClient, query)
	            .setResultCallback(new ResultCallback<DriveApi.MetadataBufferResult>() {
	            	
	        @Override
	        public void onResult(DriveApi.MetadataBufferResult result) {
	            // Success! Handle the query result.
	        }
	        
	    });

	}

}
