package com.example.herobattler

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CharactersModel : ViewModel() {
    var characters = MutableLiveData<MutableList<Character>>()

    val fileName: String = "characterSave2"
    val seperator: String = ";"

    init {
        characters.value = mutableListOf<Character>()
    }

    fun initCharacterList(items: MutableList<Character>, context: Context){
        val characterSave = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = characterSave.edit()
        items.forEach{
            editor.putString(it.characterName, it.characterLevel+seperator+ it.characterClass+seperator+ it.characterRace+seperator+ it.characterHP)
            editor.commit()
        }
    }

    fun addCharacter(name: String, level: String, race: String, cclass: String, hp: String, context: Context) {
        characters.value!!.add(Character(name, level, race, cclass, hp))
        characters.postValue(characters.value!!.toMutableList())
        val settings = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = settings.edit()
        editor.putString(name, level+seperator + cclass+seperator + race+seperator + hp)
        editor.commit()
    }

    fun loadCharacter(context: Context) {
        val settings = context?.getSharedPreferences(fileName, Context.MODE_PRIVATE)


        //it is important that you get an editor reference!
        val editor = settings?.edit()
        val allEntries: Map<String, *> = settings?.getAll() as Map<String, *>
        Log.e("where is my key: ", allEntries.toString())
        for ((key, value) in allEntries) {
            Log.e("where is my key: ", key)

            characters.value!!.add(Character(key, parseLevel(value.toString()), parseClass(value.toString()), parseRace(value.toString()), parseHP(value.toString())))
            characters.postValue(characters.value!!.toMutableList())
        }

        characters.value = mutableListOf<Character>()
    }

    fun parseLevel(value: String): String {
        val separated: List<String> = value.split(seperator)
        return separated[0].trim()
    }

    fun parseClass(value: String): String {
        val separated: List<String> = value.split(seperator)
        return separated[1].trim()
    }

    fun parseRace(value: String): String {
        val separated: List<String> = value.split(seperator)
        return separated[2].trim()
    }

    fun parseHP(value: String): String {
        val separated: List<String> = value.split(seperator)
        return separated[3].trim()
    }
}