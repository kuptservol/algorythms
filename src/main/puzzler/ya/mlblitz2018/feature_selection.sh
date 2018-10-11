catboost fit --cd train.cd -f train.txt
catboost fstr --input-path train.txt --cd train.cd
mawk -F "\t" -vOFS="\t" 'BEGIN { ORS=" " };  {print $2} NR==30{exit}' feature_strength.tsv > top_features
