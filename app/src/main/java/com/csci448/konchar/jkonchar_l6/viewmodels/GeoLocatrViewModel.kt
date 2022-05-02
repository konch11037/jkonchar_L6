package com.csci448.konchar.jkonchar_l6.viewmodels

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.csci448.konchar.jkonchar_l6.data.database.PositionAndTimeRepository
import java.util.*

class GeoLocatrViewModel(
    private val positionAndTimeRepository: PositionAndTimeRepository,
    context: Context ):
    ViewModel() {
    val currentLocationLiveData: MutableLiveData<Location?> =
                                    MutableLiveData<Location?>(null)
    val currentAddressLiveData: MutableLiveData<String?> =
                                    MutableLiveData<String?>("")


    private val workManager = WorkManager.getInstance(context)
    private val workRequest = CharacterWorker.buildOneTimeWorkRequest()
    override val outputWorkerInfo: LiveData<WorkInfo> =
        workManager.getWorkInfoByIdLiveData(workRequest.id)


    override fun requestWebCharacter() {
        workManager.enqueueUniqueWork(CharacterWorker.UNIQUE_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            workRequest)
    }

    override fun deleteAll() {
        samodelkinRepository.deleteCharacters()
    }

    private val _characterIdLiveData=MutableLiveData<UUID>()
    override val characterListLiveData  = samodelkinRepository.getCharacters()

    override val characterLiveData =
        Transformations.switchMap(_characterIdLiveData) { characterId ->
            samodelkinRepository.getCharacter(characterId)
        }

    override fun addCharacter(character: SamodelkinCharacter) {
        samodelkinRepository.addCharacter(character = character)
    }




    override fun loadCharacter(id: UUID) {
        _characterIdLiveData.value = id
    }

    override fun deleteCharacter(id: UUID) {
        samodelkinRepository.deleteCharacter(id = id)
    }


    override fun generateRandomCharacter(): SamodelkinCharacter {
        return CharacterGenerator.generateRandomCharacter()
    }

    //  init {
//            characterListLiveData.value?.let { characterlist ->
//                for (i in 1..20) {
//                    characterlist.add(charactergenerator.generaterandomcharacter())
//                }
//                characterListLiveData.value = characterlist
//            }
    // }

}