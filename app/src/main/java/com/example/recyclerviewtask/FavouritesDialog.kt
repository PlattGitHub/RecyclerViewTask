package com.example.recyclerviewtask

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

/**
 * Simple [DialogFragment] subclass that helps user to decide if he wants to add/remove
 * item to/from favourites. This dialog is launched by [WeatherForecastFragment] with
 * 'StartActivityForResult pattern'.
 *
 * @author Alexander Gorin
 */
class FavouritesDialog : DialogFragment() {

    private var titleId: Int = 0
    private var position: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val item: WeatherForecast? = arguments?.getParcelable(ITEM_KEY)
        val pos = arguments?.getInt(POSITION_KEY)

        item?.let {
            titleId = if (item.isFavourite) R.string.remove_from_fav else R.string.add_to_fav
        }
        pos?.let { position = it }

        val intent = Intent()

        return AlertDialog.Builder(activity!!)
            .setTitle(String.format(getString(titleId), item?.cityName))
            .setPositiveButton(R.string.fav_dialog_pos_button) { _, _ ->
                item?.let {
                    targetFragment?.onActivityResult(
                        targetRequestCode,
                        Activity.RESULT_OK,
                        intent.apply {
                            putExtra(POSITION_ITEM_KEY, position)
                            putExtra(OPERATION_REMOVE_KEY, item.isFavourite)
                        }
                    )
                }
            }
            .setNegativeButton(R.string.fav_dialog_neg_button) { _, _ ->
                targetFragment?.onActivityResult(
                    targetRequestCode,
                    Activity.RESULT_CANCELED,
                    intent
                )
            }
            .create()
    }

    companion object {
        const val POSITION_ITEM_KEY = "POSITION_ITEM_KEY"
        const val OPERATION_REMOVE_KEY = "OPERATION_REMOVE_KEY"
        private const val ITEM_KEY = "ITEM"
        private const val POSITION_KEY = "POSITION_KEY"
        fun newInstance(item: WeatherForecast, position: Int): FavouritesDialog {
            return FavouritesDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ITEM_KEY, item)
                    putInt(POSITION_KEY, position)
                }
            }
        }
    }
}