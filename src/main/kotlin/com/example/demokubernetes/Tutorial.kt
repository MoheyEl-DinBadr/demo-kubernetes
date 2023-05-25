package com.example.demokubernetes

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

data class Tutorial(@Id val id:Int,val title:String,  val description:String, var published:Boolean)

@Repository
interface TutorialRepository: R2dbcRepository<Tutorial, Int> {

    fun  findByTitleContaining(title: String): Flux<Tutorial>;
    fun findByPublished(isPublished: Boolean): Flux<Tutorial>;

    @Modifying
    @Query(
        "INSERT INTO tutorial (id, title, description, published) VALUES (:#{#tutorial.id}, :#{#tutorial.title}, :#{#tutorial.description}, :#{#tutorial.published})"
    )
    fun save(tutorial:Tutorial):Mono<Any>

}

@RestController
@RequestMapping("/tutorials")
class TutorialController(val repository: TutorialRepository) {

    @GetMapping
    fun findAll():Flux<Tutorial> = repository.findAll();




}

@Service
class TutorialDataInitializer(val repo:TutorialRepository): ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        repo.count()
            .flatMap{initData(it)}
            .subscribe()
    }



    fun initData(count:Long?): Mono<Unit> {
        println("TutorialDataInitializer.initData1")
        if (count!! == 0L) {
            println("TutorialDataInitializer.initData")
            val tutorial1 = Tutorial(1, "title1", "description1", true)
            val tutorial2 = Tutorial(2, "title2", "description2", false)
            val tutorial3 = Tutorial(3, "title3", "description3", true)
            val tutorial4 = Tutorial(4, "title4", "description4", false)
            val tutorial5 = Tutorial(5, "title5", "description5", true)

            return repo.save(tutorial1)
                .flatMap {repo.save(tutorial2)}
                .flatMap { repo.save(tutorial3) }
                .flatMap { repo.save(tutorial4) }
                .flatMap { repo.save(tutorial4) }
                .flatMap { repo.save(tutorial5) }
                .thenReturn(Unit)
        }

        return Mono.just(Unit)

    }

}
