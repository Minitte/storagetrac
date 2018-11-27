package dpaw.com.storagetrac.database;

import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LocalStorageDatabaseReader {

    private static final android.util.Log Log = ;
    /**
     * Path to file
     */
    private String _path;

    /**
     * Constructor for a LocalStorageDatabaseReader
     * @param path to the file
     */
    public LocalStorageDatabaseReader(String path) {
        this._path = path;
    }

    /**
     * Reads the database if any at the path
     * @return
     */
    public StorageUnitDatabase Read() {
        StorageUnitDatabase db = null;

        try {

            // open stream
            FileInputStream fStream = new FileInputStream(_path);

            ObjectInputStream objStream = new ObjectInputStream(fStream);

            // write
            db = (StorageUnitDatabase) objStream.readObject();

            // close stream
            objStream.close();

            fStream.close();

        } catch (IOException ioE) {
            Log.e("DatabaseSaver", ioE.getMessage());
        } catch (ClassNotFoundException cnfE) {
            Log.e("DatabaseSaver", cnfE.getMessage());
        } finally {
            return db;
        }
    }
}
