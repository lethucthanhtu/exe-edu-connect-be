EXE: Edu-connect

# Note:
- Pull về đổi branch, không push lên main
- main đang là production => push commit = deploy
- Tech stack:
  + FE: react + tailwind + material tailwind
  + BE: java 17 + spring boot 3.2.4 + gradle 8.6
  + DB:
     + Postgre 16
     + Firebase (nếu cần realtime db?)
- DB bên render làm DB chính
- DB bên vercel làm backup (có thể dùng để chứa salt?)
- Không edit Dockerfile khi không cần thiết
- API BE xài free nên để lâu, ít xài sẽ bị delay (~50s)
- API BE 4 entry đều cùng 1 endpoint
- tất cả environment variable (ex: api_endpoint, db_usr, db_pwd, ...) đều sẽ được đặt trong .env

# Source + Connect:
- Figma:
- FE: https://github.com/lethucthanhtu/exe-edu-connect-fe
- BE: https://github.com/lethucthanhtu/exe-edu-connect-be
- DB:
	Postgre 16 (1  Gb) (render.com) (main)
  	  + name: edu-connect
    	  + host: dpg-cnvvu5qcn0vc73c9r8g0-a.singapore-postgres.render.com
  	  + port: 5432
   	  + instance: edu_connect
  	  + username: edu_connect_user
  	  + password: jJUTG4j6tZvPtgB0IVNkzrUl9fpDOdH8
	Postgre 16 (256Mb) (vercel.com) (backup)
  	  + name: edu-connect
    	  + host: ep-purple-recipe-a1sezflj-pooler.ap-southeast-1.aws.neon.tech
  	  + port: 5432
   	  + instance: verceldb
  	  + username: default
  	  + password: wjgEOs57uhoM

# Deploy:
- FE: https://theeduconnect.vercel.app/
- BE:
	https://exe-edu-connect-be.onrender.com/
	13.228.225.19
	18.142.128.26
	54.254.162.138
- DB:
	render
  	  + external DB URL: postgres://edu_connect_user:jJUTG4j6tZvPtgB0IVNkzrUl9fpDOdH8@dpg-cnvvu5qcn0vc73c9r8g0-a.singapore-postgres.render.com/edu_connect
  	  + PSQL: PGPASSWORD=jJUTG4j6tZvPtgB0IVNkzrUl9fpDOdH8 psql -h dpg-cnvvu5qcn0vc73c9r8g0-a.singapore-postgres.render.com -U edu_connect_user edu_connect
	vercel
	  + external DB URL: postgres://default:wjgEOs57uhoM@ep-purple-recipe-a1sezflj-pooler.ap-southeast-1.aws.neon.tech:5432/verceldb?sslmode=require
	  + PSQL: psql "postgres://default:wjgEOs57uhoM@ep-purple-recipe-a1sezflj.ap-southeast-1.aws.neon.tech:5432/verceldb?sslmode=require"