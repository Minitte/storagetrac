package dpaw.com.storagetrac;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.espresso.contrib.*;

import static dpaw.com.storagetrac.RecyclerViewItemCountAssertion.withItemCount;

@RunWith(AndroidJUnit4.class)
public class StorageUnitTests {

    @Rule
    public ActivityTestRule<StorageUnitList> StorageItemListActivity =
            new ActivityTestRule<>(StorageUnitList.class);

    @Test
    public void testCreateStorageUnit() {
        // Create a storage unit
        onView(withId(R.id.storageEdit)).perform(click());
        onView(withId(R.id.storageUnitCreate)).perform(click());
        onView(withId(R.id.emailText)).perform(typeText("Test Fridge"), closeSoftKeyboard());
        onView(withId(R.id.storageUnitIcon)).perform(click());
        onView(withId(R.id.fridge)).perform(click());
        onView(withId(R.id.done)).perform(click());

        // Get index of the storage unit created
        int index = StorageUnitList._storageUnitDatabase.get_storageUnits().size() - 1;

        // Check if the storage unit was successfully added to the recycler view
        onView(withId(R.id.storageView)).check(withItemCount(index + 1));
    }

    @Test
    public void testAddItemToStorageUnit() {
        // Navigate to add item view
        onView(withId(R.id.storageView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.itemCreate)).perform(click());

        // Add an item
        onView(withId(R.id.name)).perform(typeText("Test Item"), closeSoftKeyboard());
        onView(withId(R.id.itemIcon)).perform(click());
        onView(withId(R.id.milk)).perform(click());
        onView(withId(R.id.quantity)).perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.done)).perform(click());

        // Check if item was successfully added to recycler view
        onView(withId(R.id.itemView)).check(withItemCount(1));
    }

    @Test
    public void testDeleteStorageUnit() {

        // Get index of the storage unit created
        int index = StorageUnitList._storageUnitDatabase.get_storageUnits().size() - 1;

        // Delete first storage
        //onView(withId(R.id.storageEdit)).perform(click());
        onView(withId(R.id.deleteButton)).perform(click());

        // Check if item count went down
        onView(withId(R.id.storageView)).check(withItemCount(index));
    }
}
