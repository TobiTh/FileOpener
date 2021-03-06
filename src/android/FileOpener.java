/*
* PhoneGap is available under *either* the terms of the modified BSD license *or* the
* MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
*
* Copyright (c) 2005-2010, Nitobi Software Inc.
* Copyright (c) 2011, IBM Corporation
*/

package de.rwthaachen.rz.rwthapp.plugins.fileopener;

import java.io.IOException;
import java.net.URLConnection;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

public class FileOpener extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {

        try {
            if (action.equals("openFile")) {
               if( openFile(args.getString(0)) ) {
                   callbackContext.success();
               }
               else {
                   callbackContext.error("Could not open file");
               }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            callbackContext.error(e.getMessage());
        } catch (RuntimeException e) { // KLUDGE for Activity Not Found
            e.printStackTrace();
            callbackContext.error(e.getMessage());
        }
        return false;
    }

    private boolean openFile(String url) throws IOException {
        // Create URI
        Uri uri = Uri.parse(url);

        Intent intent;
        // Check what kind of file you are trying to open, by comparing the url with extensions.
        // When the if condition is matched, plugin sets the correct intent (mime) type,
        // so Android knew what application to use to open the file

        
        String mimeType = URLConnection.guessContentTypeFromName(url);
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, mimeType);
        
        try
        {
              this.cordova.getActivity().startActivity(intent);
              return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

}
