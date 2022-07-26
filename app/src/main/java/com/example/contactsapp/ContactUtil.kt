package com.example.contactsapp

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.os.SystemClock
import android.provider.ContactsContract
import android.util.Log
import contacts.core.Contacts
import contacts.core.util.emailList
import contacts.core.util.organizationList
import contacts.core.util.phoneList

object ContactUtil {
    fun getAllContactsFromLibrary(context: Context): ArrayList<ContactsModel>? {

        val contactsModalArrayList: ArrayList<ContactsModel> = arrayListOf()

        val start: Long = SystemClock.uptimeMillis()

        val contacts = Contacts(context).query().find()
        Log.e("checkme", "getAllContacts: $contacts")

        val end: Long = SystemClock.uptimeMillis()
        Log.e("END", "TimeForContacts " + (end - start).toString() + " ms")

        for (i in contacts) {
            if (!i.phoneList().isNullOrEmpty())
                for (j in i.phoneList()){

                    Log.e("checkme", "getAllContacts: "+  i.displayNamePrimary+if (!i.phoneList().isNullOrEmpty())j.normalizedNumber else null+
                        if (!i.organizationList().isNullOrEmpty()) i.organizationList()
                            .get(0).company else null+
                        if (!i.emailList().isNullOrEmpty()) i.emailList().get(0).address else null )
            contactsModalArrayList.add(
                ContactsModel(
                    i.displayNamePrimary,
                    if (!i.phoneList().isNullOrEmpty())j.normalizedNumber else null,
                    if (!i.organizationList().isNullOrEmpty()) i.organizationList()
                        .get(0).company else null,
                    if (!i.emailList().isNullOrEmpty()) i.emailList().get(0).label else null
                )
            );}

        }
        return contactsModalArrayList

    }

    @SuppressLint("Range")
    fun getAllContactsoldie(context: Context): ArrayList<ContactsModel> {
        val contactsModalArrayList: ArrayList<ContactsModel> = arrayListOf()
        val numList: ArrayList<String> = arrayListOf()

        val startnow: Long = SystemClock.uptimeMillis()
        var contactId = ""
        var displayName = ""
        var company = ""
        var email = ""
        val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI

        val selection = ContactsContract.Contacts.HAS_PHONE_NUMBER
        val cursor: Cursor = context.applicationContext.getContentResolver().query(
            uri,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Organization.COMPANY,
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.Contacts._ID,
                ContactsContract.RawContacts.ACCOUNT_TYPE
            ),
            selection,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )!!
        if (cursor.count > 0) {
            while (cursor.moveToNext()) {
                contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                displayName =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val phoneNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER))
                company =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY))
                email =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
//                Log.e(
//                    "checkme",
//                    "getContacts: $displayName$phoneNumber$company$email$contactId"
//                )
                if (!phoneNumber.isNullOrBlank())
                if (!numList.contains(phoneNumber.replace("\\s".toRegex(), ""))){
                    numList.add(phoneNumber.replace("\\s".toRegex(), ""))
                    contactsModalArrayList.add(ContactsModel(displayName, phoneNumber, company, email))
                Log.e(
                    "checkme",
                    "getContacts: $displayName$phoneNumber$company$email$contactId"
                )
                }
            }
        }

        cursor.close()

        val endnow: Long = SystemClock.uptimeMillis()
        Log.e("END", "TimeForContacts " + (endnow - startnow) + " ms")
        return contactsModalArrayList
    }
}