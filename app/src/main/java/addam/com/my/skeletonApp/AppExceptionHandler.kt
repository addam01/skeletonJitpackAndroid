package addam.com.my.skeletonApp

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Process


class AppExceptionHandler(private val systemHandler: Thread.UncaughtExceptionHandler,
                          private val crashlyticsHandler: Thread.UncaughtExceptionHandler,
                          application: Application) : Thread.UncaughtExceptionHandler {

    private var lastStartedActivity: Activity? = null

    private var startCount = 0

    init {
        application.registerActivityLifecycleCallbacks(
                object : Application.ActivityLifecycleCallbacks {
                    override fun onActivityPaused(activity: Activity?) {
                        // empty
                    }

                    override fun onActivityResumed(activity: Activity?) {
                        // empty
                    }

                    override fun onActivityStarted(activity: Activity?) {
                        startCount++
                        lastStartedActivity = activity
                    }

                    override fun onActivityDestroyed(activity: Activity?) {
                        // empty
                    }

                    override fun onActivitySaveInstanceState(activity: Activity?,
                                                             outState: Bundle?) {
                        // empty
                    }

                    override fun onActivityStopped(activity: Activity?) {
                        startCount--
                        if (startCount <= 0) {
                            lastStartedActivity = null
                        }
                    }

                    override fun onActivityCreated(activity: Activity?,
                                                   savedInstanceState: Bundle?) {
                        // empty
                    }
                })
    }


    override fun uncaughtException(t: Thread?, e: Throwable) {
//        e.printStackTrace()

        lastStartedActivity?.let { activity ->
            //            Crashlytics.log(e.printStackTrace().toString())
//            Crashlytics.getInstance().core.log(e.message)
//            Crashlytics.getInstance().core.logException(e)

//            killThisProcess {
//                val intent = Intent(activity, CrashActivity::class.java).apply {
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                }
                with(activity) {
                    finish()
                    startActivity(intent)
//                }
            }


            /*val isRestarted = activity.intent
                    .getBooleanExtra(RESTARTED, false)

            val lastException = activity.intent
                    .getSerializableExtra(LAST_EXCEPTION) as Throwable?

            if (!isRestarted || !isSameException(e, lastException)) {
                // signal exception to be logged by crashlytics
                crashlyticsHandler.uncaughtException(t, e)

                // restart the application
                killThisProcess {
                    val intent = activity.intent
                            .putExtra(RESTARTED, true)
                            .putExtra(LAST_EXCEPTION, e)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK)

                    with(activity) {
                        finish()
                        startActivity(intent)
                    }
                }
            } else {
                //The default system exception handler will handle the caught exception.
                killThisProcess {
                    systemHandler.uncaughtException(t, e)
                }
            }*/
        } ?: killThisProcess {
            crashlyticsHandler.uncaughtException(t, e)
            systemHandler.uncaughtException(t, e)
        }
    }

    /**
     * Not bullet-proof, but it works well.
     */
    private fun isSameException(originalException: Throwable,
                                lastException: Throwable?): Boolean {
        if (lastException == null) return false

        return originalException.javaClass == lastException.javaClass &&
                originalException.stackTrace[0] == originalException.stackTrace[0] &&
                originalException.message == lastException.message
    }

    private fun killThisProcess(action: () -> Unit = {}) {
        action()

        Process.killProcess(Process.myPid())
        System.exit(10)
    }

    companion object {
        private const val RESTARTED = "appExceptionHandler_restarted"
        private const val LAST_EXCEPTION = "appExceptionHandler_lastException"
    }
}