# Contributing

* If you make changes, please make your changes on a branch, then open a pull-request.
* Code Reviews are ***REQUIRED***, on all pull-requests. (This is also enforced by Github.)
* All PRs should mention the issue(s) that they address, preferably in one of the [magic formats][].
    * The goal of this is that `master` is ***always*** working. It should always be safe to fork
      master, and if you fork master and break something, it should always be your fault.
* Every task on the SCRUM board in the STREAM basement should also have a corresponding GH Issue.
  This issue number should be written on the paper task. The status of the task should also match
  the state of the PR. (ex. Open = Backlog, `in progress` label = In Progress, Closed = Complete)
    * Please make sure to tag your issues!
    * As soon as someone is assigned on the SCRUM board, they should be assigned in Github.
    * Please put your issues in the appropriate milestone!
	* Feel free to open issues for really, really far-off features. Just give them a future milestone.
    * Just generally keep GH Issues in sync with the SCRUM Board.
* Commit early, Commit often.
* Commit guidelines! Commits should always be written in the second person imperative, with the first
  letter lowercase, and no period at the end (?, ! and &#8253; are OK though). This means that your
  commit message should tell anyone looking at it _what the commit will do to the codebase_, for
  example:
    * Good:
	* `add laser cannons`
	* `add laser cannons!`
    * Bad:
	* `Add laser cannons.`
	* `ADD LASER CANNONS`
	* `aDD LASER CANNONS`
	* `Addition: laser cannons`
	* `Added the laser cannons`
	* `I added the laser cannons`
	* `We added the laser cannons`
	* `Noah added the laser cannons`
	* `Will add the laser cannons`
	* `commit message`
	* `changed a thing`
	* `fixed it`
	* `<crickets>`
	* etc.
    * I ([@ariporad][]) personally use the [angular.js commit message guidelines][ang-cmg], and you
      should too! But I won't force you. For the record, It's basically:
	* `<feat|fix|perf|doc|style|test>[(scope)]: message` For example:
	    * `feat(lasers): add laser cannons`
	    * `fix: don't crash on lunch.`
	    * etc.


[@ariporad]: https://github.com/ariporad "@ariporad"
[ang-cmg]: https://github.com/angular/angular.js/blob/master/CONTRIBUTING.md#commit
[magic formats]: https://github.com/blog/1506-closing-issues-via-pull-requests
