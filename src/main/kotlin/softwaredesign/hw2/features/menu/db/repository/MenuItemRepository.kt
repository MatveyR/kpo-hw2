package softwaredesign.hw2.features.menu.db.repository

import org.springframework.data.jpa.repository.JpaRepository
import softwaredesign.hw2.features.menu.db.enitites.MenuItem

interface MenuItemRepository : JpaRepository<MenuItem, Long> {
    fun findByName(name: String?): MenuItem?
    fun findById(id: Long?): MenuItem?
}