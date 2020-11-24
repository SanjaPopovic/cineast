#!/bin/bash
# use ./vbs_setup.sh /tank/import/ 3 (3 being the number of threads), inside cineast-folder
git pull
./gradlew clean cineast-runtime:fatJar
./gradlew cineast-api:fatJar

timestamp(){
  date +"%m-%d_%H-%M-%S"
}

# restart cottontail and start a new tmux session with cottontail dumping to a new logfile
restart_cottontail () {
  echo "restarting cottontail"
  kill $(pgrep --full cottontail)
  cd ..
  cd cottontaildb || exit
  git pull
  ./gradlew clean shadowJar
  tmux new-window -d -n cottontaildb "java -jar build/libs/cottontaildb-0.10.1-all.jar >> cottontail_$(timestamp).log"
  cd ..
  cd cineast || exit
}

# base-folder
base=$1
threads=$2
sleep=5
currtime=$(timestamp)
# clean logfiles
rm *.log
rm ../cottontaildb/*.log
restart_cottontail
sleep $sleep
echo "setting up"
java -jar cineast-runtime/build/libs/cineast-runtime-3.0.2-full.jar vbs-import.json setup --clean >> vbs_setup_$currtime.log
echo "importing proto files"
java -jar cineast-runtime/build/libs/cineast-runtime-3.0.2-full.jar vbs-import.json import --no-finalize --type PROTO --input $base/extracted_combined/ --batchsize 400 --threads $threads >> proto_import_$currtime.log
echo "importing text & metadata"
java -jar cineast-runtime/build/libs/cineast-runtime-3.0.2-full.jar vbs-import.json import --no-finalize --type AUDIOTRANSCRIPTION --input $base/text/audiomerge.json --threads $threads --batchsize 15000 >> audio_import_$currtime.log
java -jar cineast-runtime/build/libs/cineast-runtime-3.0.2-full.jar vbs-import.json import --no-finalize --type CAPTIONING --input $base/text/captions.json --threads $threads >> captioning_import_$currtime.log
java -jar cineast-runtime/build/libs/cineast-runtime-3.0.2-full.jar vbs-import.json import --no-finalize --type GOOGLEVISION --input $base/text/gvision.json --threads $threads >> gvision_import_$currtime.log
java -jar cineast-runtime/build/libs/cineast-runtime-3.0.2-full.jar vbs-import.json import --no-finalize --type METADATA --input $base/text/metamerge.json --threads $threads --batchsize 25000 >> md_import_$currtime.log
java -jar cineast-runtime/build/libs/cineast-runtime-3.0.2-full.jar vbs-import.json import --no-finalize --type TAGS --input $base/text/V3C1Tags --threads $threads --batchsize 35000 >> tags_import_$currtime.log
java -jar cineast-runtime/build/libs/cineast-runtime-3.0.2-full.jar vbs-import.json import --no-finalize --type V3C1CLASSIFICATIONS --input $base/text/V3C1Analysis --threads $threads --batchsize 25000 >> text_import_$currtime.log
java -jar cineast-runtime/build/libs/cineast-runtime-3.0.2-full.jar vbs-import.json import --no-finalize --type V3C1FACES --input $base/text/V3C1Analysis/faces --threads $threads --batchsize 25000 >> text_import_$currtime.log
echo "importing mlt-features"
java -jar cineast-runtime/build/libs/cineast-runtime-3.0.2-full.jar vbs-import.json import --no-finalize --type OBJECTINSTANCE --input $base/features.csv --threads $threads --batchsize 400 >> mlt_import_$currtime.log

sleep $sleep
echo "optimizing"
java -jar cineast-runtime/build/libs/cineast-runtime-3.0.2-full.jar vbs-import.json optimize >> vbs_optimize_$currtime.log
sleep $sleep
rm -r cineast_cache_*
tmux new-window -d -n cineast "java -jar cineast-api/build/libs/cineast-api-3.0.2-full.jar vbs-import.json"
