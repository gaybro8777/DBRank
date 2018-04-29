package com.om.dbrank.data.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class RealmUserEntity(
    @PrimaryKey
    private var id: Int = 0,
    private var name: String? = null,
    private var email: String? = null,
    private var age: Int = 0,
    private var occupation: String? = null,
    private var nationality: String? = null
) : RealmObject()