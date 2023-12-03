package com.duycomp.autoclicker.feature.overlay.setting

//class SettingDialogViewModel(
//    private val userDataRepository: UserDataRepositoryImpl,
//    private val configDatabaseRepository: ClickerConfigDatabaseRepositoryImpl,
//): ViewModel() {
//    val configClick = _configClick.asStateFlow()
//    fun onConfigNameChange(value: String) = viewModelScope.launch {
//        _configClick.value = _configClick.value.copy(configName = value)
//        configDatabaseRepository.update(_configClick.value)
//    }
//
//    fun onTimerScheduleChange(timerSchedule: TimerSchedule) = viewModelScope.launch {
//        _configClick.value = _configClick.value.copy(timerSchedule = timerSchedule)
//        configDatabaseRepository.update(_configClick.value)
//    }
//
//    fun onLoopChange(value: Int) = viewModelScope.launch {
//        _configClick.value = _configClick.value.copy(nLoop = value)
//        configDatabaseRepository.update(_configClick.value)
//    }
//
//    fun onInfinityLoopChange(value: Boolean) = viewModelScope.launch {
//        configDatabaseRepository.update(_configClick.value)
//    }
//
//    fun onSaveConfig(configClick: ConfigClick) = viewModelScope.launch {
//        configDatabaseRepository.insert(configClick)
//    }
//
//
//}