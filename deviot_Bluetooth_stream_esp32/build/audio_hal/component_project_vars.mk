# Automatically generated build file. Do not edit.
COMPONENT_INCLUDES += /home/tuandoan/esp/esp-adf/components/audio_hal/include /home/tuandoan/esp/esp-adf/components/audio_hal/driver/include /home/tuandoan/esp/esp-adf/components/audio_hal/driver/es8388 /home/tuandoan/esp/esp-adf/components/audio_hal/driver/es8374 /home/tuandoan/esp/esp-adf/components/audio_hal/driver/es8311 /home/tuandoan/esp/esp-adf/components/audio_hal/driver/es7243 /home/tuandoan/esp/esp-adf/components/audio_hal/driver/zl38063 /home/tuandoan/esp/esp-adf/components/audio_hal/driver/zl38063/api_lib /home/tuandoan/esp/esp-adf/components/audio_hal/driver/zl38063/example_apps /home/tuandoan/esp/esp-adf/components/audio_hal/driver/zl38063/firmware /home/tuandoan/esp/esp-adf/components/audio_hal/driver/tas5805m /home/tuandoan/esp/esp-adf/components/audio_hal/driver/es7148
COMPONENT_LDFLAGS += -L$(BUILD_DIR_BASE)/audio_hal -laudio_hal -L/home/tuandoan/esp/esp-adf/components/audio_hal/driver/zl38063/firmware -lfirmware
COMPONENT_LINKER_DEPS += 
COMPONENT_SUBMODULES += 
COMPONENT_LIBRARIES += audio_hal
COMPONENT_LDFRAGMENTS += 
component-audio_hal-build: 
