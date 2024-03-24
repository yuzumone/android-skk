package jp.deadend.noname.skk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.view.inputmethod.InputConnection

class KeyMapperBroadcastReceiver(ims: SKKService) : BroadcastReceiver() {
    companion object {
        val KEY_MAPPER_INPUT_METHOD_ACTION_INPUT_DOWN_UP =
            "io.github.sds100.keymapper.inputmethod.ACTION_INPUT_DOWN_UP"
        val KEY_MAPPER_INPUT_METHOD_ACTION_INPUT_DOWN =
            "io.github.sds100.keymapper.inputmethod.ACTION_INPUT_DOWN"
        val KEY_MAPPER_INPUT_METHOD_ACTION_INPUT_UP =
            "io.github.sds100.keymapper.inputmethod.ACTION_INPUT_UP"
        val KEY_MAPPER_INPUT_METHOD_ACTION_TEXT =
            "io.github.sds100.keymapper.inputmethod.ACTION_INPUT_TEXT"
        val KEY_MAPPER_INPUT_METHOD_EXTRA_TEXT = "io.github.sds100.keymapper.inputmethod.EXTRA_TEXT"
        val KEY_MAPPER_INPUT_METHOD_EXTRA_KEY_EVENT =
            "io.github.sds100.keymapper.inputmethod.EXTRA_KEY_EVENT"
    }
    private val mIms: SKKService

    init {
        mIms = ims
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action!!
        when (action) {
            KEY_MAPPER_INPUT_METHOD_ACTION_INPUT_DOWN_UP -> {
                val downEvent =
                    intent.getParcelableExtra<KeyEvent>(KEY_MAPPER_INPUT_METHOD_EXTRA_KEY_EVENT)
                val ic: InputConnection = mIms.currentInputConnection
                ic.sendKeyEvent(downEvent)
                val upEvent = KeyEvent.changeAction(downEvent, KeyEvent.ACTION_UP)
                ic.sendKeyEvent(upEvent)
            }

            KEY_MAPPER_INPUT_METHOD_ACTION_INPUT_DOWN -> {
                var downEvent =
                    intent.getParcelableExtra<KeyEvent>(KEY_MAPPER_INPUT_METHOD_EXTRA_KEY_EVENT)
                downEvent = KeyEvent.changeAction(downEvent, KeyEvent.ACTION_DOWN)
                val ic: InputConnection = mIms.currentInputConnection
                ic.sendKeyEvent(downEvent)
            }

            KEY_MAPPER_INPUT_METHOD_ACTION_INPUT_UP -> {
                var upEvent =
                    intent.getParcelableExtra<KeyEvent>(KEY_MAPPER_INPUT_METHOD_EXTRA_KEY_EVENT)
                upEvent = KeyEvent.changeAction(upEvent, KeyEvent.ACTION_UP)
                val ic: InputConnection = mIms.currentInputConnection
                ic.sendKeyEvent(upEvent)
            }

            KEY_MAPPER_INPUT_METHOD_ACTION_TEXT -> {
                val text = intent.getStringExtra(KEY_MAPPER_INPUT_METHOD_EXTRA_TEXT) ?: return
                if (mIms.currentInputConnection == null) return
                mIms.currentInputConnection.commitText(text, 1)
            }
        }
    }
}