# Count lines of code
#
# 2019-01-27: About 1000 vs 7500


echo -n "Deep Space: "
cd src/main/java/robot/deepspace
git ls-files | xargs cat | wc -l

echo -n "Camera    : "
cd ../camera
git ls-files | xargs cat | wc -l

echo -n "Total     : "
cd ../..
git ls-files | xargs cat | wc -l
