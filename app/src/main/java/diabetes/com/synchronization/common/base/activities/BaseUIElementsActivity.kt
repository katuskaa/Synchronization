package diabetes.com.synchronization.common.base.activities

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import diabetes.com.synchronization.R
import kotlinx.android.synthetic.main.base_activity__activity_layout.*

abstract class BaseUIElementsActivity : BaseActivity() {

    protected lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var toolbar: Toolbar
    protected lateinit var activityLayout: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.base_activity__activity_layout)
        this.activityLayout = layoutInflater.inflate(this.setActivityLayout(), this.contentFL)
        this.initializeToolbar()
        this.initializeNavigationDrawer(activityLayout)
    }

    private fun initializeToolbar() {
        val toolbar: Int? = this.setToolbar()
        toolbar?.let {
            this.toolbar = this.findViewById(toolbar)
            this.toolbar.setNavigationOnClickListener { this@BaseUIElementsActivity.onBackPressed() }
            this.toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        }
    }

    private fun initializeNavigationDrawer(activityLayout: View) {
        val drawer: Int? = this.setDrawer()
        drawer?.let {
            this.drawerLayout = activityLayout.findViewById<DrawerLayout>(drawer)
            this.drawerLayout.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
            this.actionBarDrawerToggle = ActionBarDrawerToggle(this, this.drawerLayout, this.toolbar, 0, 0)
            this.drawerLayout.addDrawerListener(this.actionBarDrawerToggle)
        }
    }

    protected abstract fun setActivityLayout(): Int

    protected abstract fun setDrawer(): Int?

    protected abstract fun setToolbar(): Int?
}