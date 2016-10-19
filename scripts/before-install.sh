echo 'killing existing flask process if any'
sudo kill -9 $(sudo lsof -i :8050 | grep LISTEN) >> processkill.log 2>&1 &