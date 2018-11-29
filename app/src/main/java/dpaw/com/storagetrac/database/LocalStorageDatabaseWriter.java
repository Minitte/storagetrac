package dpaw.com.storagetrac.database;

import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class LocalStorageDatabaseWriter {

    /**
     * Path to write to
     */
    private String _path;

    /**
     * Constructor of a database writer that writes to local storage
     * @param path
     */
    public LocalStorageDatabaseWriter(String path) {
        this._path = path;
    }

    /**
     * Writes the database to the path
     * @param db
     */
    public void write(StorageUnitDatabase db) {
        try {
            // open stream
            FileOutputStream fStream = new FileOutputStream(_path);

            ObjectOutputStream objStream = new ObjectOutputStream(fStream);

            // write
            objStream.writeObject(db);

            // close stream
            objStream.close();

            fStream.close();

            Log.i("DatabaseWriter", "Successfully wrote database to local storage");

        } catch (IOException ioe) {
            Log.e("DatabaseWriter", ioe.getMessage());
        }
    }
}
