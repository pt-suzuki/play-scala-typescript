execute "scaladownload" do
  user "root"
  command "wget http://downloads.typesafe.com/scala/2.12.6/scala-2.12.6.tgz"
  action :run
end

execute "scalaunzip" do
  user "root"
  command "tar xvzf scala-2.12.6.tgz"
  action :run
end

execute "scala_install" do
  user "root"
  command "sudo mv scala-2.12.6 /usr/local/lib/scala"
  action :run
end

execute "scala_valid" do
  user "root"
  command "echo 'export SCALA_HOME=/usr/local/lib/scala' >> /etc/profile.d/scala.sh"
  action :run
end

execute "scala_valid2" do
  user "root"
  command "echo 'export PATH=$PATH:$SCALA_HOME/bin' >> /etc/profile.d/scala.sh"
  action :run
end
